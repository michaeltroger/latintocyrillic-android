package at.mikenet.serbianlatintocyrillic.tools

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.customize.CustomizeActivity

object LanguageSwitch {

    fun openLanguageSwitchDialog(context: Context, onDismissCallback: (lang: String) -> Unit) {
        var lang = ""
        val items = context.resources.getStringArray(R.array.languageChosen);
        val values = context.resources.getStringArray(R.array.languageChosenValues);
        AlertDialog.Builder(context)
                .setItems(items) { dialogInterface, number ->
                    lang = values[number].orEmpty()
                    dialogInterface.dismiss()
                }
                .setOnDismissListener {
                    if (lang.isEmpty()) return@setOnDismissListener
                    onDismissCallback(lang)
                    if (lang == MyPreferenceConstants.Value.ChosenLanguage.CUSTOM &&
                            !PreferenceTools.hasCustomAlphabet(context)) {
                        AlertDialog.Builder(context)
                                .setMessage(context.getString(R.string.alert_conversion_without_alphabet_title))
                                .setPositiveButton(context.getString(R.string.alert_conversion_without_alphabet_yes)) { _, _ ->
                                    val i = Intent(context, CustomizeActivity::class.java)
                                    context.startActivity(i)
                                }
                                .setNegativeButton(context.getString(R.string.alert_conversion_without_alphabet_no)) { _, _ ->
                                    // nothing to do
                                }
                                .show()
                    }
                }
                .setNegativeButton(context.getString(R.string.customize_choose_template_cancel_button)) { _, _ ->
                    // nothing to do
                }
                .show()
    }

}