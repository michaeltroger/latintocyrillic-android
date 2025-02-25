package at.mikenet.serbianlatintocyrillic.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import at.mikenet.serbianlatintocyrillic.MainActivity
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.alphabet.AlphabetFragmentDirections
import at.mikenet.serbianlatintocyrillic.settings.SettingsFragmentDirections
import at.mikenet.serbianlatintocyrillic.tools.LanguageSwitch
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants

abstract class ConverterFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener, MenuProvider {

    private var shouldUpdateLanguage = false

    protected lateinit var viewModel: ConverterViewModel

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            MyPreferenceConstants.Key.CUSTOM_LATIN,
            MyPreferenceConstants.Key.CUSTOM_CYRILLIC,
            MyPreferenceConstants.Key.LANGUAGE_CHOSEN -> shouldUpdateLanguage = true
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(requireContext())
        if (shouldUpdateLanguage) {
            shouldUpdateLanguage = false
            viewModel.updateLanguage(requireContext())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.languageButtonString().observe(viewLifecycleOwner, Observer {
            updateLanguageButton(it)
        })

        viewModel.allowConversion().observe(viewLifecycleOwner, Observer {
            if (it == null || !it) return@Observer
            startAllowingConversion()
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.show_alphabet -> {
                showAlphabet()
                return true
            }
            R.id.menu_settings -> {
                findNavController().navigate(SettingsFragmentDirections.actionGlobalSettingsFragment())
                return true
            }
        }
        return false
    }

    private fun showAlphabet() {
        findNavController().navigate(AlphabetFragmentDirections.actionGlobalAlphabetFragment())
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveText(requireContext())
    }

    abstract fun updateLanguageButton(text: String)

    abstract fun startAllowingConversion()

    fun openAlphabet() {
        showAlphabet()
    }

    fun openLanguageSwitchDialog() {
        LanguageSwitch.openLanguageSwitchDialog(requireContext(), findNavController()) {
            lang -> viewModel.updateLanguage(requireContext(), lang)
        }
    }

    fun share(text: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}