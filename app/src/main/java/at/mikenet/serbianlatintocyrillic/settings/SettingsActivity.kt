package at.mikenet.serbianlatintocyrillic.settings


import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import at.mikenet.serbianlatintocyrillic.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_settings)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
