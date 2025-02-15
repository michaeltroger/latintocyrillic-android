package at.mikenet.serbianlatintocyrillic.customize

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.extensions.applySystemInsets


class CustomizeActivity : AppCompatActivity(R.layout.activity_customize) {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24px)

        applySystemInsets(R.id.customize_fragment)
    }
}
