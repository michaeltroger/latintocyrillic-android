package at.mikenet.serbianlatintocyrillic.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun AppCompatActivity.applySystemInsets(layoutId: Int) {
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(layoutId)) { v, insets ->
        val bars: Insets = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout()
        )
        v.updatePadding(
            top = bars.top,
            bottom = bars.bottom,
            left = bars.left,
            right = bars.right,
        )
        WindowInsetsCompat.CONSUMED
    }
}
