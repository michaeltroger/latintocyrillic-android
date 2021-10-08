package at.mikenet.serbianlatintocyrillic.alphabet

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import com.michaeltroger.latintocyrillic.Cyrillic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AlphabetViewModel(app: Application) : AndroidViewModel(app) {

    private var converter: Cyrillic? = null
    private val _latinAlphabet = MutableLiveData<String>()
    private val _cyrillicAlphabet = MutableLiveData<String>()
    private val _alphabetName = MutableLiveData<String>()
    private val _language = MutableLiveData<String>().apply {
        value = ""
    }
    private var systemLanguage: String? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            init(app, PreferenceTools.languageChosen(app))
            systemLanguage = Locale.getDefault().language
        }
    }

    fun language(): LiveData<String> {
        return _language
    }

    fun alphabetName(): LiveData<String> {
        return _alphabetName
    }

    fun latinAlphabet(): LiveData<String> {
        return _latinAlphabet
    }

    fun cyrillicAlphabet(): LiveData<String> {
        return _cyrillicAlphabet
    }

    fun updateLanguage(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            init(context, PreferenceTools.languageChosen(context))
        }
    }

    fun updateLanguage(context: Context, lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            PreferenceTools.setLanguageChosen(context, lang)
            init(context, lang)
        }
    }

    fun onResume(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val currentSystemLang = Locale.getDefault().language
            if (systemLanguage != currentSystemLang) {
                systemLanguage = currentSystemLang
                _alphabetName.postValue(PreferenceTools.getLocalizedAlphabetName(context))
            }
        }
    }

    private suspend fun init(context: Context, language: String) = withContext(Dispatchers.IO) {
        converter = PreferenceTools.getAlphabetRepo(context, language)

        _alphabetName.postValue(PreferenceTools.getLocalizedAlphabetName(context))
        _language.postValue(language)

        val convert = converter
        if (convert != null) {
            _latinAlphabet.postValue(convert.getLatinAlphabet().joinToString(separator = "\n"))
            _cyrillicAlphabet.postValue(convert.getCyrillicAlphabet().joinToString(separator = "\n"))
        }
    }

}