package at.mikenet.serbianlatintocyrillic.settings

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>(MyPreferenceConstants.Key.LANGUAGE_CHOSEN)?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                val languageChosen = newValue as String
                if (languageChosen == MyPreferenceConstants.Value.ChosenLanguage.CUSTOM &&
                        !PreferenceTools.hasCustomAlphabet(context)) {
                    AlertDialog.Builder(context)
                            .setMessage(getString(R.string.alert_conversion_without_alphabet_title))
                            .setPositiveButton(getString(R.string.alert_conversion_without_alphabet_yes)) { _, _ ->
                                openCustomizeActivity()
                            }
                            .setNegativeButton(getString(R.string.alert_conversion_without_alphabet_no)) { _, _ ->
                                // nothing to do
                            }
                            .show()
                }
                true
            }
        }

        findPreference<Preference>(MyPreferenceConstants.Key.CUSTOM_ALPHABET)?.apply {
            setOnPreferenceClickListener {
                openCustomizeActivity()
                true
            }
        }

        findPreference<Preference>(MyPreferenceConstants.Key.RESTORE_ON_START)?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                val disableAutoCopy = !(newValue as Boolean)
                if (disableAutoCopy) {
                    PreferenceTools.saveText(context, "", isCyrillic = false)
                }
                true
            }
        }

        findPreference<ListPreference>(MyPreferenceConstants.Key.THEME)?.apply {

            val code = Build.VERSION.SDK_INT
            when {
                code == Build.VERSION_CODES.P -> {
                    entries = arrayOf(
                            getString(R.string.theme_light),
                            getString(R.string.theme_dark),
                            getString(R.string.theme_follow_system_not_supported),
                            getString(R.string.theme_battery)
                    )

                    entryValues = arrayOf(
                            MyPreferenceConstants.Value.Theme.LIGHT,
                            MyPreferenceConstants.Value.Theme.DARK,
                            MyPreferenceConstants.Value.Theme.FOLLOW_SYSTEM,
                            MyPreferenceConstants.Value.Theme.BATTERY
                    )
                }
                code > Build.VERSION_CODES.P -> {
                    entries = arrayOf(
                                getString(R.string.theme_light),
                                getString(R.string.theme_dark),
                                getString(R.string.theme_follow_system)
                        )

                    entryValues = arrayOf(
                            MyPreferenceConstants.Value.Theme.LIGHT,
                            MyPreferenceConstants.Value.Theme.DARK,
                            MyPreferenceConstants.Value.Theme.FOLLOW_SYSTEM
                    )
                }
                else -> {
                    entries = arrayOf(
                            getString(R.string.theme_light),
                            getString(R.string.theme_dark),
                            getString(R.string.theme_battery)
                    )
                    entryValues = arrayOf(
                            MyPreferenceConstants.Value.Theme.LIGHT,
                            MyPreferenceConstants.Value.Theme.DARK,
                            MyPreferenceConstants.Value.Theme.BATTERY
                    )
                }
            }

            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    AppCompatDelegate.setDefaultNightMode(PreferenceTools.getThemeFromString(requireContext(), newValue))
                }

                true
            }
        }
    }

    private fun openCustomizeActivity() {
        findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToCustomizeFragment(lang = null))
    }

}