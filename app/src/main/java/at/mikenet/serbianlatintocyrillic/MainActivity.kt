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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import at.mikenet.serbianlatintocyrillic.extensions.applySystemInsets
import at.mikenet.serbianlatintocyrillic.main.ConverterViewModel
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import com.vorlonsoft.android.rate.AppRate


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var layoutChangeRequested = false
    val viewModel by viewModels<ConverterViewModel>()

    private var navController: NavController? = null
    private val appBarConfiguration = AppBarConfiguration.Builder(
        R.id.autoConvertLayoutFragment,
        R.id.sideBySideLayoutFragment,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        setContentView(R.layout.activity_main)

        applySystemInsets(R.id.main_fragment_container)

        handleIncomingTextIntent()

        setUpNavigation()

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
                navController?.navigate(R.id.autoConvertLayoutFragment)
                PreferenceTools.saveUseAutoConvertLayout(baseContext, true)
            }
            R.id.side_by_side_layout -> {
                navController?.navigate(R.id.sideBySideLayoutFragment)
                PreferenceTools.saveUseAutoConvertLayout(baseContext, false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (layoutChangeRequested) {
            layoutChangeRequested = false
            updateLayout()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() == true || super.onSupportNavigateUp()
    }

    private fun updateLayout() {
        if (PreferenceTools.useAutoConvertLayout(baseContext)) {
            navController?.navigate(R.id.autoConvertLayoutFragment)
        } else {
            navController?.navigate(R.id.sideBySideLayoutFragment)
        }
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        navController = navHostFragment.navController.apply {
            graph =  navInflater.inflate(R.navigation.nav_graph).apply {
                if (PreferenceTools.useAutoConvertLayout(baseContext)) {
                    setStartDestination(R.id.autoConvertLayoutFragment)
                } else {
                    setStartDestination(R.id.sideBySideLayoutFragment)
                }
            }
        }.also {
            setupActionBarWithNavController(
                navController = it,
                configuration = appBarConfiguration.build()
            )
        }
    }
}
