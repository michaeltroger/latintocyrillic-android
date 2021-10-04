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
import at.mikenet.serbianlatintocyrillic.tools.MyPreferenceConstants
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import kotlinx.android.synthetic.main.fragment_customize.*


class CustomizeFragment : Fragment() {

    companion object {
        const val EXTRA_LANGUAGE = "lang"
    }

    private val viewModel by viewModels<CustomizeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_customize, container, false)
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
            if (customize_latin.childCount != it.size) {
                customize_latin.removeAllViews()
                it.forEachIndexed { i, text ->
                    addLatinView(i, text)
                }
            }
        })

        viewModel.getCyrillic().observe(viewLifecycleOwner, Observer {
            if (customize_cyrillic.childCount != it.size) {
                customize_cyrillic.removeAllViews()
                it.forEachIndexed { i, text ->
                    addCyrillicView(i, text)
                }
            }

        })

        viewModel.enableRowButton().observe(viewLifecycleOwner, Observer {
            if (!it) return@Observer

            add_row_button.setOnClickListener {
                activity?.currentFocus?.clearFocus()
                viewModel.addRow()
                addLatinView(customize_latin.childCount, "")
                addCyrillicView(customize_cyrillic.childCount, "")
            }
            add_row_button.isEnabled = true
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

            save_button.setOnClickListener {
                onSaveClicked()
            }
            save_button.isEnabled = true
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
        customize_cyrillic.addView(
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
        customize_latin.addView(
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
