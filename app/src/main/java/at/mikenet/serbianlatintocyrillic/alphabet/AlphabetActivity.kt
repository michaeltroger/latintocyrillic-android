package at.mikenet.serbianlatintocyrillic.alphabet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.extensions.applySystemInsets

class AlphabetActivity : AppCompatActivity(R.layout.activity_alphabet) {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        applySystemInsets(R.id.alphabet_fragment)
    }

}
