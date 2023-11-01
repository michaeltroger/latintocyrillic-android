package at.mikenet.serbianlatintocyrillic.main

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import at.mikenet.serbianlatintocyrillic.R
import kotlinx.coroutines.launch


class AutoConvertLayoutFragment : ConverterFragment() {

    private lateinit var autoConvertText: EditText
    private lateinit var autoConvertButton: Button
    private lateinit var languageButton: Button
    private lateinit var changeLanguageButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_auto_convert_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autoConvertText = view.findViewById(R.id.auto_convert_text)
        autoConvertButton = view.findViewById(R.id.autoConvertButton)
        languageButton = view.findViewById(R.id.language)
        changeLanguageButton = view.findViewById(R.id.change_language)

        autoConvertButton.setOnClickListener {
            autoConvert()
        }
        languageButton.setOnClickListener {
            openAlphabet()
        }
        changeLanguageButton.setOnClickListener {
            openLanguageSwitchDialog()
        }
        changeLanguageButton.isClickable = false

        val liveData = viewModel.getText()
        liveData.observe(viewLifecycleOwner) {
            view.findViewById<EditText>(R.id.auto_convert_text)?.setText(it.first)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clear_all -> {
                autoConvertText.setText("")
                return true
            }
            R.id.copy_text -> {
                viewModel.copyText(autoConvertText.text.toString(), requireContext())
                return true
            }
            R.id.share_text -> {
                share(autoConvertText.text.toString())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun autoConvert() {
        lifecycleScope.launch {
            autoConvertButton.isEnabled = false

            val input = autoConvertText.text.toString()

            val isCyrillic = viewModel.isCyrillic(input)
            val output = if (isCyrillic) {
                viewModel.cyrillicToLatin(input, requireContext())
            } else {
                viewModel.latinToCyrillic(input, requireContext())
            }
            autoConvertText.setText(output)

            autoConvertButton.isEnabled = true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.auto_convert_menu, menu)
    }

    override fun updateLanguageButton(text: String) {
        languageButton.text = text
    }

    override fun startAllowingConversion() {
        autoConvertText.isEnabled = true
        autoConvertButton.isEnabled = true
        autoConvertText.doAfterTextChanged {
            if (it != null) {
                viewModel.setCachedText(it.toString())
            }
        }

        changeLanguageButton.isClickable = true
    }

}
