package com.michaeltroger.serbianlatintocyrillic.repo

import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepoI

/**
 * Both must be of same size and correctly sorted
 */
class CustomAlphabetRepo(latin: List<String>, cyrillic: List<String>) : LatinCyrillicAlphabetRepoI() {

    override val latinToCyrillicAlphabet by lazy {
        var i = 0
        latin.associateWith {
            cyrillic[i++]
        }
    }
}
