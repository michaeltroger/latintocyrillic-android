package com.michaeltroger.serbianlatintocyrillic

import com.michaeltroger.serbianlatintocyrillic.repo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CyrillicImpl : Cyrillic {

    private lateinit var repo: LatinCyrillicAlphabetRepo

    constructor(latin: List<String>, cyrillic: List<Char>) {
        this.repo = CustomAlphabetRepo(latin, cyrillic)
    }

    constructor(alphabet: Alphabet) {
        this.repo = when (alphabet) {
            Alphabet.BelarusianIso9 -> BelarusianIso9AlphabetRepo()
            Alphabet.BulgarianIso9 -> BulgarianIso9AlphabetRepo()
            Alphabet.Macedonian -> MacedonianAlphabetRepo()
            Alphabet.MacedonianIso9 -> MacedonianIso9AlphabetRepo()
            Alphabet.RussianIso9 -> RussianIso9AlphabetRepo()
            Alphabet.Serbian -> SerbianAlphabetRepo()
            Alphabet.UkrainianIso9 -> UkrainianIso9AlphabetRepo()
        }
    }

    private val latinToCyrillicMap by lazy {
        repo.getLatinToCyrillicMap()
    }
    private val cyrillicToLatinMap by lazy {
        repo.getCyrillicToLatinMap()
    }

    override suspend fun latinToCyrillic(text: String): String = withContext(Dispatchers.Default) {
        val convertedText = StringBuilder()
        var i = 0
        while (i < text.length) {
            val oneLookedUpChar = latinToCyrillicMap[text[i].toString()]
            val twoLookedUpChars = if (i + 1 >= text.length) null else latinToCyrillicMap[text[i].toString() + text[i + 1]]
            when {
                oneLookedUpChar.isNullOrEmpty() && twoLookedUpChars.isNullOrEmpty() -> convertedText.append(text[i]) // don't convert unknown chars
                twoLookedUpChars.isNullOrEmpty() -> convertedText.append(oneLookedUpChar)
                else -> {
                    convertedText.append(twoLookedUpChars)
                    i++
                }
            }
            i++
        }
        convertedText.toString()
    }

    override suspend fun cyrillicToLatin(text: String): String = withContext(Dispatchers.Default) {
        val convertedText = StringBuilder()
        text.forEach {
            val lookedUpChar = cyrillicToLatinMap[it.toString()]
            if (lookedUpChar.isNullOrEmpty()) {  // don't convert unknown chars
                convertedText.append(it)
            } else {
                convertedText.append(lookedUpChar)
            }
        }
        convertedText.toString()
    }

    override suspend fun isCyrillic(text: String): Boolean {
        text.forEachIndexed { i, t ->
            val oneChar = t.toString()
            val twoChars = if (i + 1 >= text.length) null else t.toString() + text[i + 1]
            if (cyrillicToLatinMap.containsKey(oneChar)) {
                return true
            } else if (latinToCyrillicMap.containsKey(oneChar) || twoChars != null && latinToCyrillicMap.containsKey(twoChars)) {
                return false
            }
        }

        return false
    }


    override val latinAlphabet: List<String>
        get() = latinToCyrillicMap.toSortedMap().keys.toList()

    override val cyrillicAlphabet: List<String>
        get() = latinToCyrillicMap.toSortedMap().values.toList()

}