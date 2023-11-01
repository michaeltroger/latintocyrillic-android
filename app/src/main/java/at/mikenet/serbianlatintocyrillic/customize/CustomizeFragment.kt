package at.mikenet.serbianlatintocyrillic.customize


import android.os.Bundle
import android.text.InputFilter
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.databinding.FragmentCustomizeBinding
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools


class CustomizeFragment : Fragment() {

    companion object {
        const val EXTRA_LANGUAGE = "lang"
    }

    private lateinit var binding: FragmentCustomizeBinding

    private val viewModel by viewModels<CustomizeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentCustomizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preselectedLang = activity?.intent?.getStringExtra(EXTRA_LANGUAGE)
        if (preselectedLang == null) {
            viewModel.getChooseTemplateDialog().observe(viewLifecycleOwner, Observer<Boolean> {
                if (it) {
                    var lang = ""
                    val items = resources.getStringArray(R.array.languageChosen);
                    val values = resources.getStringArray(R.array.languageChosenValues);
                    AlertDialog.Builder(requireContext())
                        .setItems(items) { dialogInterface, number ->
                            lang = values[number].orEmpty()
                            dialogInterface.dismiss()
                        }
                        .setOnDismissListener {
                            viewModel.initViewModel(lang, requireContext())
                        }
                        .setNegativeButton(getString(R.string.customize_choose_template_cancel_button)) { _, _ ->
                            // nothing to do
                        }.setTitle(getString(R.string.customize_choose_template_title))
                        .show()
                }

            })
        } else {
            viewModel.initViewModel(preselectedLang, requireContext())
        }

        viewModel.getLatin().observe(viewLifecycleOwner, Observer {
            if (binding.customizeLatin.childCount != it.size) {
                binding.customizeLatin.removeAllViews()
                it.forEachIndexed { i, text ->
                    addLatinView(i, text)
                }
            }
        })

        viewModel.getCyrillic().observe(viewLifecycleOwner, Observer {
            if (binding.customizeCyrillic.childCount != it.size) {
                binding.customizeCyrillic.removeAllViews()
                it.forEachIndexed { i, text ->
                    addCyrillicView(i, text)
                }
            }

        })

        viewModel.enableRowButton().observe(viewLifecycleOwner, Observer {
            if (!it) return@Observer

            binding.addRowButton.setOnClickListener {
                activity?.currentFocus?.clearFocus()
                viewModel.addRow()
                addLatinView(binding.customizeLatin.childCount, "")
                addCyrillicView(binding.customizeCyrillic.childCount, "")
            }
            binding.addRowButton.isEnabled = true
        })

        viewModel.getShowUseAsDefaultDialog().observe(viewLifecycleOwner, Observer {
            if (it && PreferenceTools.languageChosen(requireContext()) != MyPreferenceConstants.Value.ChosenLanguage.CUSTOM) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.customize_default_title))
                    .setPositiveButton(getString(R.string.customize_default_ok)) { _, _ ->
                        PreferenceTools.setCustomLanguage(requireContext())
                        activity?.finish()
                    }
                    .setNegativeButton(getString(R.string.customize_default_no)) { _, _ ->
                        activity?.finish()
                    }.setOnCancelListener {
                        activity?.finish()
                    }.show()
            } else {
                activity?.finish()
            }
        })

        viewModel.enableSaveButton().observe(viewLifecycleOwner, Observer {
            if (!it) return@Observer

            binding.saveButton.setOnClickListener {
                onSaveClicked()
            }
            binding.saveButton.isEnabled = true
        })
    }

    private fun onSaveClicked() {
        activity?.currentFocus?.clearFocus()
        if (PreferenceTools.hasCustomAlphabet(requireContext())) {
            AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.customize_overwrite_warning_title))
                    .setPositiveButton(getString(R.string.customize_overwrite_warning_ok)) { _, _ ->
                        viewModel.save(requireContext())
                    }
                    .setNegativeButton(getString(R.string.customize_overwrite_warning_cancel)) { _, _ ->
                        // nothing to do
                    }
                    .show()
        } else {
            viewModel.save(requireContext())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save_custom -> {
                onSaveClicked()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.customize_menu, menu)

        viewModel.enableSaveButton().observe(viewLifecycleOwner, Observer {
            if (!it) return@Observer

            menu.getItem(0).isEnabled = true
        })
    }

    private fun addCyrillicView(i: Int, text: String) {
        binding.customizeCyrillic.addView(
            EditText(context).apply {
                setText(text)
                filters += InputFilter.LengthFilter(1)
                doOnTextChanged { text, _, _, _ ->
                    viewModel.cyrillicEntryChanged(i, text.toString())
                }
                hint = context.getString(R.string.customize_cyrillic_hint)
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.END
            }
        )
    }

    private fun addLatinView(i: Int, text: String) {
        binding.customizeLatin.addView(
            EditText(context).apply {
                setText(text)
                filters += InputFilter.LengthFilter(2)
                doOnTextChanged { text, _, _, _ ->
                    viewModel.latinEntryChanged(i, text.toString())
                }
                hint = context.getString(R.string.customize_latin_hint)
                setSingleLine()
                ellipsize = TextUtils.TruncateAt.END
            }
        )
    }

}
