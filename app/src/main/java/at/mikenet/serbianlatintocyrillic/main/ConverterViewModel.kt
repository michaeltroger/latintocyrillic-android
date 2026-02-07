package at.mikenet.serbianlatintocyrillic.main

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.mikenet.serbianlatintocyrillic.R
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools.setLanguageChosen
import com.michaeltroger.latintocyrillic.LatinCyrillic
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class ConverterViewModel(app: Application) : AndroidViewModel(app) {

    private var myClipboard: ClipboardManager? = null
    private val _text = MutableLiveData<Pair<String, Boolean>>()
    private val _allowConversion = MutableLiveData<Boolean>()
    private val _languageButtonString = MutableLiveData<String>()
    private var converter: LatinCyrillic? = null
    private var toast: Toast? = null
    private var systemLanguage: String? = null
    private var textCached: Pair<String, Boolean>? = null
    private var intentText = ""

    init {
        myClipboard = app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        viewModelScope.launch(Dispatchers.IO) {
            init(app, PreferenceTools.languageChosen(app))
            if (intentText.isNotEmpty()) {
                val isCyrillic = isCyrillic(intentText)
                setCachedText(intentText, isCyrillic)
                _text.postValue(textCached!!)
            } else if (PreferenceTools.restoreOnStart(app)) {
                val text = PreferenceTools.getSavedText(app)
                val isCyrillic = PreferenceTools.getSavedTextIsCyrillic(app)
                setCachedText(text, isCyrillic)
                _text.postValue(textCached!!)
            }
            _allowConversion.postValue(true)
            systemLanguage = Locale.getDefault().language
        }

    }

    fun onResume(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentSystemLang = Locale.getDefault().language
            if (systemLanguage != currentSystemLang) {
                systemLanguage = currentSystemLang
                _languageButtonString.postValue(PreferenceTools.getLocalizedAlphabetName(context))
            }
        }
    }

    fun languageButtonString(): LiveData<String> {
        return _languageButtonString
    }

    fun allowConversion(): LiveData<Boolean> {
        return _allowConversion
    }

    fun getText(): LiveData<Pair<String, Boolean>> {
        return _text
    }

    fun setCachedText(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setCachedText(text, isCyrillic(text))
        }

    }

    fun setCachedText(text: String, isCyrillic: Boolean) {
        textCached = Pair(text, isCyrillic)
    }

    /**
     * could be called before init is done
     */
    fun setTextFromIntent(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (converter == null) {
                intentText = text
            } else {
                setCachedText(text, isCyrillic(text))
                _text.postValue(textCached!!)
            }
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveText(context: Context) {
        textCached?.let {
            _text.value = it
        }
        GlobalScope.launch(Dispatchers.IO) {
            textCached?.let {
                PreferenceTools.saveText(context, it.first, it.second)
            }
        }

    }

    fun updateLanguage(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            init(context, PreferenceTools.languageChosen(context))
        }
    }

    fun updateLanguage(context: Context, lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            setLanguageChosen(context, lang)
            init(context, lang)
        }
    }

    private suspend fun init(context: Context, lang: String) = withContext(Dispatchers.IO) {
        converter = PreferenceTools.getAlphabetRepo(context, lang)
        _languageButtonString.postValue(PreferenceTools.getLocalizedAlphabetName(context, lang))
    }

    suspend fun cyrillicToLatin(text: String, context: Context): String {
        val output = converter?.cyrillicToLatin(text) ?: ""
        if (shouldCopyText(context)) {
            copyText(output, context)
        }
        return output
    }

    suspend fun latinToCyrillic(text: String, context: Context): String {
        val output = converter?.latinToCyrillic(text) ?: ""
        if (shouldCopyText(context)) {
            copyText(output, context)
        }

        return output
    }

    suspend fun isCyrillic(text: String) = converter?.isCyrillic(text) ?: false

    fun copyText(text: String, context: Context) {
        val clipBoard = myClipboard
        if (clipBoard != null) {
            clipBoard.setPrimaryClip(ClipData.newPlainText("clip", text))

            toast?.cancel()
            toast = Toast.makeText(
                context,
                context.getString(R.string.text_copied_toast),
                Toast.LENGTH_SHORT
            )
            toast?.show()
        }
    }

    private suspend fun shouldCopyText(context: Context) = withContext(Dispatchers.IO) {
        PreferenceTools.shouldTextBeAutoCopied(context)
    }

}