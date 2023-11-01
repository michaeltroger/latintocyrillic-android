package at.mikenet.serbianlatintocyrillic.alphabet


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.customize.CustomizeActivity
import at.mikenet.serbianlatintocyrillic.customize.CustomizeFragment
import at.mikenet.serbianlatintocyrillic.databinding.FragmentAlphabetBinding
import at.mikenet.serbianlatintocyrillic.tools.LanguageSwitch
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants


class AlphabetFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private var shouldUpdateLanguage = false
    private val viewModel by viewModels<AlphabetViewModel>()

    private lateinit var binding: FragmentAlphabetBinding

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            MyPreferenceConstants.Key.CUSTOM_LATIN,
            MyPreferenceConstants.Key.CUSTOM_CYRILLIC,
            MyPreferenceConstants.Key.LANGUAGE_CHOSEN -> shouldUpdateLanguage = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentAlphabetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.alphabetName().observe(viewLifecycleOwner, Observer {
            binding.alphabetName.text = it
        })

        viewModel.latinAlphabet().observe(viewLifecycleOwner, Observer {
            binding.alphabetL.text = it
        })

        viewModel.cyrillicAlphabet().observe(viewLifecycleOwner, Observer {
            binding.alphabetC.text = it
        })

        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (shouldUpdateLanguage) {
            shouldUpdateLanguage = false
            viewModel.updateLanguage(requireContext())
        }
        viewModel.onResume(requireContext())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_alphabet -> {
                changeAlphabet()
                return true
            }
            R.id.menu_adapt_alphabet -> {
                openCustomizeActivity()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeAlphabet() {
        LanguageSwitch.openLanguageSwitchDialog(requireContext()) {
            lang -> viewModel.updateLanguage(requireContext(), lang)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.alphabet_menu, menu)
    }

    private fun openCustomizeActivity() {
        val i = Intent(context, CustomizeActivity::class.java)
        i.putExtra(CustomizeFragment.EXTRA_LANGUAGE, viewModel.language().value)
        startActivity(i)
    }

}
