package com.michaeltroger.serbianlatintocyrillic.repo

import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepo

/**
 * Both must be of same size and correctly sorted
 */
public class CustomAlphabetRepo(latin: List<String>, cyrillic: List<String>) : LatinCyrillicAlphabetRepo() {

    override val latinToCyrillicAlphabet: Map<String, String> by lazy {
        var i = 0
        latin.associateWith {
            cyrillic[i++]
        }
    }
}
