package at.mikenet.serbianlatintocyrillic.tools

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.CyrillicFactory
import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillic

object PreferenceTools {

    fun getLocalizedAlphabetName(context: Context, lang: String = languageChosen(context)): String {
        if (lang.isEmpty()) return ""
        val resID = context.resources.getIdentifier(lang, "string", context.packageName)
        return context.resources.getString(resID)
    }

    fun languageChosen(context: Context): String {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getString(MyPreferenceConstants.Key.LANGUAGE_CHOSEN, MyPreferenceConstants.Value.ChosenLanguage.SERBIAN) ?: MyPreferenceConstants.Value.ChosenLanguage.SERBIAN
    }

    fun setLanguageChosen(context: Context, lang: String) {
        if (lang.isEmpty()) return
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putString(MyPreferenceConstants.Key.LANGUAGE_CHOSEN, lang).apply()
    }

    fun saveText(context: Context, text: String, isCyrillic: Boolean) {
        val settings = PreferenceManager.getDefaultSharedPreferences(context).edit()
        settings.putString(MyPreferenceConstants.Key.SAVED_TEXT, text)
                .apply()
        settings.putBoolean(MyPreferenceConstants.Key.SAVED_TEXT_IS_CYRILLIC, isCyrillic)
                .apply()
    }

    fun getSavedText(context: Context): String {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getString(MyPreferenceConstants.Key.SAVED_TEXT, "") ?: ""
    }

    fun getSavedTextIsCyrillic(context: Context): Boolean {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getBoolean(MyPreferenceConstants.Key.SAVED_TEXT_IS_CYRILLIC, false)
    }

    fun setCustomLanguage(context: Context) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPrefs
                .edit()
                .putString(
                        MyPreferenceConstants.Key.LANGUAGE_CHOSEN,
                        MyPreferenceConstants.Value.ChosenLanguage.CUSTOM
                )
                .apply()
    }

    fun shouldTextBeAutoCopied(context: Context): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean(MyPreferenceConstants.Key.AUTO_COPY, false)
    }

    fun useAutoConvertLayout(context: Context): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean(MyPreferenceConstants.Key.ALTERNATIVE_LAYOUT, false)
    }

    fun saveUseAutoConvertLayout(context: Context, useAltLayout: Boolean) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPrefs.putBoolean(MyPreferenceConstants.Key.ALTERNATIVE_LAYOUT, useAltLayout).apply()
    }

    fun getTheme(context: Context): Int {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return getThemeFromString(context, sharedPrefs.getString(MyPreferenceConstants.Key.THEME, "default")!!)
    }

    fun getThemeFromString(context: Context, theme: String): Int {
        val code = Build.VERSION.SDK_INT
        val nightModeFlags = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return when(theme) {
            MyPreferenceConstants.Value.Theme.LIGHT ->  AppCompatDelegate.MODE_NIGHT_NO
            MyPreferenceConstants.Value.Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
            MyPreferenceConstants.Value.Theme.BATTERY -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            MyPreferenceConstants.Value.Theme.FOLLOW_SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> when {
                code == Build.VERSION_CODES.P -> {
                    when (nightModeFlags) {
                        Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        else -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                }
                code >= Build.VERSION_CODES.Q -> {
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                else -> {
                    AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                }

            }

        }
    }

    fun restoreOnStart(context: Context): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPrefs.getBoolean(MyPreferenceConstants.Key.RESTORE_ON_START, true)
    }

    fun saveCustomAlphabet(context: Context, latin: List<String>, cyrillic: List<String>) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()

        val gson = Gson()
        editor.putString(MyPreferenceConstants.Key.CUSTOM_LATIN, gson.toJson(latin)).apply()
        editor.putString(MyPreferenceConstants.Key.CUSTOM_CYRILLIC, gson.toJson(cyrillic)).apply()
    }

    fun getAlphabetRepo(context: Context, lang: String): LatinToCyrillic {
        return when(lang) {
            MyPreferenceConstants.Value.ChosenLanguage.BELARUSIAN_ISO9 -> CyrillicFactory.createConverter(Alphabet.BelarusianIso9)
            MyPreferenceConstants.Value.ChosenLanguage.BULGARIAN_ISO9 -> CyrillicFactory.createConverter(Alphabet.BulgarianIso9)
            MyPreferenceConstants.Value.ChosenLanguage.MACEDONIAN -> CyrillicFactory.createConverter(Alphabet.Macedonian)
            MyPreferenceConstants.Value.ChosenLanguage.MACEDONIAN_ISO9 -> CyrillicFactory.createConverter(Alphabet.MacedonianIso9)
            MyPreferenceConstants.Value.ChosenLanguage.RUSSIAN_ISO9 -> CyrillicFactory.createConverter(Alphabet.RussianIso9)
            MyPreferenceConstants.Value.ChosenLanguage.SERBIAN -> CyrillicFactory.createConverter(Alphabet.Serbian)
            MyPreferenceConstants.Value.ChosenLanguage.UKRAINIAN_ISO9 -> CyrillicFactory.createConverter(Alphabet.UkrainianIso9)
            MyPreferenceConstants.Value.ChosenLanguage.CUSTOM ->  getCustomAlphabetRepo(context)
            else -> CyrillicFactory.createConverter(Alphabet.Serbian)
        }
    }

    private fun getCustomAlphabetRepo(context: Context): LatinToCyrillic {
        val latin: List<String>
        val cyrillic: List<String>
        if (hasCustomAlphabet(context)) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val latinString = prefs.getString(MyPreferenceConstants.Key.CUSTOM_LATIN, "")
            val cyrillicString = prefs.getString(MyPreferenceConstants.Key.CUSTOM_CYRILLIC, "")

            val gson = Gson()
            val itemType = object : TypeToken<List<String>>() {}.type

            latin = gson.fromJson(latinString, itemType)
            cyrillic = gson.fromJson(cyrillicString, itemType)
        } else {
            latin = emptyList()
            cyrillic = emptyList()
        }

        return CyrillicFactory.createConverter(latin = latin, cyrillic = cyrillic)
    }

    fun hasCustomAlphabet(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return !prefs.getString(MyPreferenceConstants.Key.CUSTOM_LATIN, "").isNullOrEmpty() &&
                !prefs.getString(MyPreferenceConstants.Key.CUSTOM_CYRILLIC, "").isNullOrEmpty()
    }
}
