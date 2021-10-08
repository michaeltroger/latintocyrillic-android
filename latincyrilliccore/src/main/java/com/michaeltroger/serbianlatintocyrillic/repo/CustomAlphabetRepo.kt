package com.michaeltroger.serbianlatintocyrillic.repo

import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepo
import java.lang.IllegalArgumentException

/**
 * Both must be of same size and correctly sorted
 */
internal class CustomAlphabetRepo(latin: List<String>, cyrillic: List<Char>) : LatinCyrillicAlphabetRepo() {

    init {
        if (latin.size != cyrillic.size) {
            throw IllegalArgumentException("Latin and Cyrillic alphabets must have same size. But have: Latin:${latin.size}/Cyrillic:${cyrillic.size}")
        }
    }

    override val latinToCyrillicAlphabet: Map<String, String> by lazy {
        var i = 0
        latin.associateWith {
            cyrillic[i++].toString()
        }
    }
}
