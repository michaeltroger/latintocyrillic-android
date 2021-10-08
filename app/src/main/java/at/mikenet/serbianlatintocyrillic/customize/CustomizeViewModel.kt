package at.mikenet.serbianlatintocyrillic.customize

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.mikenet.serbianlatintocyrillic.tools.PreferenceTools
import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillicImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomizeViewModel: ViewModel() {

    private val _cyrillicAlphabet = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }
    private val _latinAlphabet = MutableLiveData<MutableList<String>>().apply {
        value = mutableListOf()
    }
    private val _showUseAsDefaultDialog = MutableLiveData<Boolean>()
    private val _showChooseTemplateDialog = MutableLiveData<Boolean>()
    private val _enableSaveButton = MutableLiveData<Boolean>()
    private val _enableAddRowButton = MutableLiveData<Boolean>()

    init {
        _showChooseTemplateDialog.value = true
    }

    fun initViewModel(lang: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (lang.isNotEmpty()) {
                val repo = PreferenceTools.getAlphabetRepo(context, lang)
                val converter = LatinToCyrillicImpl(repo)
                _cyrillicAlphabet.postValue(converter.cyrillicAlphabet.toMutableList())
                _latinAlphabet.postValue(converter.latinAlphabet.toMutableList())
            }
            _enableAddRowButton.postValue(true)
            _enableSaveButton.postValue(true)
            _showChooseTemplateDialog.postValue(false)

        }
    }

    fun getLatin() : LiveData<MutableList<String>> {
        return _latinAlphabet
    }

    fun getCyrillic() : LiveData<MutableList<String>> {
        return _cyrillicAlphabet
    }

    fun enableSaveButton(): LiveData<Boolean> {
        return _enableSaveButton
    }

    fun enableRowButton(): LiveData<Boolean> {
        return _enableAddRowButton
    }

    fun getShowUseAsDefaultDialog(): LiveData<Boolean> {
        return _showUseAsDefaultDialog
    }

    fun getChooseTemplateDialog(): LiveData<Boolean> {
        return _showChooseTemplateDialog
    }

    fun latinEntryChanged(index: Int, text: String) {
        _latinAlphabet.value?.set(index, text)
    }

    fun cyrillicEntryChanged(index: Int, text: String) {
        _cyrillicAlphabet.value?.set(index, text)
    }

    fun save(context: Context) {
        var userLatin = _latinAlphabet.value
        var userCyrillic = _cyrillicAlphabet.value

        if (userLatin == null || userCyrillic == null) {
            userLatin = mutableListOf()
            userCyrillic = mutableListOf()
        }

        val latinFiltered = mutableListOf<String>()
        val cyrillicFiltered = mutableListOf<String>()

        userLatin.forEachIndexed { i, latinText ->
            val cyrillicText = userCyrillic[i]
            if (latinText.isNotEmpty() && cyrillicText.isNotEmpty()) {
                latinFiltered.add(latinText)
                cyrillicFiltered.add(cyrillicText)
            }
        }

        PreferenceTools.saveCustomAlphabet(context, latinFiltered, cyrillicFiltered)

        _showUseAsDefaultDialog.value = true
    }

    fun addRow() {
        _latinAlphabet.value?.add("")
        _cyrillicAlphabet.value?.add("")
    }

}