package at.mikenet.serbianlatintocyrillic

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(PreferenceTools.getTheme(baseContext))
    }
}