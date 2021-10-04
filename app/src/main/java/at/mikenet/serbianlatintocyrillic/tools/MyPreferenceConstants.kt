package at.mikenet.serbianlatintocyrillic.tools

object MyPreferenceConstants {

    object Key {
        const val CUSTOM_LATIN = "customLatin"
        const val CUSTOM_CYRILLIC = "customCyrillic"
        const val CUSTOM_ALPHABET = "customAlphabet"
        const val LANGUAGE_CHOSEN = "prefLanguageChosen"
        const val THEME = "prefThemeNight"
        const val AUTO_COPY = "prefAutoCopy"
        const val ALTERNATIVE_LAYOUT = "prefAltLayout"
        const val RESTORE_ON_START = "prefOnExit"
        const val SAVED_TEXT = "text"
        const val SAVED_TEXT_IS_CYRILLIC = "text_is_cyrillic"
    }

    object Value {
        object ChosenLanguage {
            const val BELARUSIAN_ISO9 = "belarusian_iso9"
            const val BULGARIAN_ISO9 = "bulgarian_iso9"
            const val SERBIAN = "serbian"
            const val MACEDONIAN = "macedonian"
            const val MACEDONIAN_ISO9 = "macedonian_iso9"
            const val RUSSIAN_ISO9 = "russian_iso9"
            const val UKRAINIAN_ISO9 = "ukrainian_iso9"
            const val CUSTOM = "custom"
        }
        object Theme {
            const val LIGHT = "light"
            const val DARK = "dark"
            const val BATTERY = "battery"
            const val FOLLOW_SYSTEM = "follow_system"
        }
    }

}