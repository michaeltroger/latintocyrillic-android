package at.mikenet.serbianlatintocyrillic.main

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import at.mikenet.serbianlatintocyrillic.R
import kotlinx.coroutines.launch


class SideBySideLayoutFragment : ConverterFragment() {

    private lateinit var cyrillicText: EditText
    private lateinit var latinText: EditText
    private lateinit var languageButton: Button
    private lateinit var latinToCyrillicButton: ImageButton
    private lateinit var cyrillicToLatinButton: ImageButton
    private lateinit var changeLanguageButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_side_by_side_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cyrillicText = view.findViewById(R.id.cyrillic_text)
        latinText = view.findViewById(R.id.latin_text)
        latinToCyrillicButton = view.findViewById(R.id.latinToCyrillicButton)
        cyrillicToLatinButton = view.findViewById(R.id.cyrillicToLatinButton)
        languageButton = view.findViewById(R.id.language)
        changeLanguageButton = view.findViewById(R.id.change_language)

        latinToCyrillicButton.setOnClickListener {
            latinToCyrillic()
        }
        cyrillicToLatinButton.setOnClickListener {
            cyrillicToLatin()
        }
        languageButton.setOnClickListener {
            openAlphabet()
        }
        changeLanguageButton.setOnClickListener {
            openLanguageSwitchDialog()
        }
        changeLanguageButton.isClickable = false

        val liveData = viewModel.getText()
        liveData.observe(viewLifecycleOwner, Observer<Pair<String, Boolean>> {
            if (it != null) {
                val (text, isCyrillic) = it
                if (isCyrillic) {
                    view.findViewById<EditText>(R.id.cyrillic_text)?.setText(text)
                } else {
                    view.findViewById<EditText>(R.id.latin_text)?.setText(text)
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_all -> {
                latinText.setText("")
                cyrillicText.setText("")
                return true
            }
            R.id.copy_latin -> {
                viewModel.copyText(latinText.text.toString(), requireContext())
                return true
            }
            R.id.copy_cyrillic -> {
                viewModel.copyText(cyrillicText.text.toString(), requireContext())
                return true
            }
            R.id.share_cyrillic -> {
                share(cyrillicText.text.toString())
                return true
            }
            R.id.share_latin -> {
                share(latinText.text.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun latinToCyrillic() {
        lifecycleScope.launch {
            latinToCyrillicButton.isEnabled = false
            cyrillicToLatinButton.isEnabled = false

            val input = latinText.text.toString()

            val output = viewModel.latinToCyrillic(input, requireContext())

            cyrillicText.setText(output)

            cyrillicToLatinButton.isEnabled = true
            latinToCyrillicButton.isEnabled = true
        }
    }

    private fun cyrillicToLatin() {
        lifecycleScope.launch {
            latinToCyrillicButton.isEnabled = false
            cyrillicToLatinButton.isEnabled = false

            val input = cyrillicText.text.toString()

            val output = viewModel.cyrillicToLatin(input, requireContext())

            latinText.setText(output)

            cyrillicToLatinButton.isEnabled = true
            latinToCyrillicButton.isEnabled = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.side_by_side_menu, menu)
    }

    override fun updateLanguageButton(text: String) {
        languageButton.text = text
    }

    override fun startAllowingConversion() {
        cyrillicText.isEnabled = true
        latinText.isEnabled = true
        cyrillicToLatinButton.isEnabled = true
        latinToCyrillicButton.isEnabled = true

        latinText.doAfterTextChanged {
            if (it != null) {
                viewModel.setCachedText(it.toString(), isCyrillic = false)
            }
        }

        cyrillicText.doAfterTextChanged {
            if (it != null) {
                viewModel.setCachedText(it.toString(), isCyrillic = true)
            }
        }

        changeLanguageButton.isClickable = true
    }
}
