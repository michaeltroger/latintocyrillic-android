package at.mikenet.serbianlatintocyrillic


import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import at.mikenet.serbianlatintocyrillic.extensions.applySystemInsets
import at.mikenet.serbianlatintocyrillic.main.AutoConvertLayoutFragment
import at.mikenet.serbianlatintocyrillic.main.ConverterViewModel
import at.mikenet.serbianlatintocyrillic.main.SideBySideLayoutFragment
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import com.vorlonsoft.android.rate.AppRate


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var layoutChangeRequested = false
    val viewModel by viewModels<ConverterViewModel>()

    private companion object {
        const val SIDE_BY_SIDE_FRAGMENT_TAG = "sidebyside"
        const val AUTO_CONVERT_FRAGMENT_TAG = "autoconvert"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        setContentView(R.layout.activity_main)

        applySystemInsets(R.id.main_root)

        handleIncomingTextIntent()

        setLayout()

        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> window.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            )
        }

        AppRate.with(this)
            .setStoreType(getString(R.string.rate_uri))
            .setInstallDays(0.toByte())           // default is 10, 0 means install day, 10 means app is launched 10 or more days later than installation
            .setLaunchTimes(3.toByte())           // default is 10, 3 means app is launched 3 or more times
            .setRemindInterval(1.toByte())        // default is 1, 1 means app is launched 1 or more days after neutral button clicked
            .setRemindLaunchesNumber(1.toByte())  // default is 0, 1 means app is launched 1 or more times after neutral button clicked
            .monitor()                            // Monitors the app launch times
        AppRate.showRateDialogIfMeetsConditions(this) // Shows the Rate Dialog when conditions are met
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIncomingTextIntent()
    }

    private fun handleIncomingTextIntent() {
        if (intent?.action == Intent.ACTION_SEND && "text/plain" == intent.type) {
            intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                viewModel.setTextFromIntent(it)
            }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key) {
            MyPreferenceConstants.Key.ALTERNATIVE_LAYOUT -> layoutChangeRequested = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.auto_convert_layout -> {
                setAutoConvertFragmentIfNotSetYet()
                PreferenceTools.saveUseAutoConvertLayout(baseContext, true)
            }
            R.id.side_by_side_layout -> {
                setSideBySideFragmentIfNotSetYet()
                PreferenceTools.saveUseAutoConvertLayout(baseContext, false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (layoutChangeRequested) {
            layoutChangeRequested = false
            setLayout()
        }

    }

    private fun setLayout() {
        if (PreferenceTools.useAutoConvertLayout(baseContext)) {
            setAutoConvertFragmentIfNotSetYet()
        } else {
            setSideBySideFragmentIfNotSetYet()
        }
    }

    private fun setAutoConvertFragmentIfNotSetYet() {
        if (supportFragmentManager.findFragmentByTag(AUTO_CONVERT_FRAGMENT_TAG) == null) {
            supportFragmentManager.commit {
                replace(R.id.main_root, AutoConvertLayoutFragment(), AUTO_CONVERT_FRAGMENT_TAG)
            }
        }
    }

    private fun setSideBySideFragmentIfNotSetYet() {
        if (supportFragmentManager.findFragmentByTag(SIDE_BY_SIDE_FRAGMENT_TAG) == null) {
            supportFragmentManager.commit {
                replace(R.id.main_root, SideBySideLayoutFragment(), SIDE_BY_SIDE_FRAGMENT_TAG)
            }
        }
    }
}
