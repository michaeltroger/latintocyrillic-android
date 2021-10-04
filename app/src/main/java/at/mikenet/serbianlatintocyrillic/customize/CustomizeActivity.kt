package at.mikenet.serbianlatintocyrillic.customize

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import at.mikenet.serbianlatintocyrillic.R


class CustomizeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24px);
    }

}
