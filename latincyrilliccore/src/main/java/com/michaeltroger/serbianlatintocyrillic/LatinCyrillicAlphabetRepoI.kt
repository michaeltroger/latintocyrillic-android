package com.michaeltroger.serbianlatintocyrillic

/**
 * Supports 1 or 2 latin script characters translated to 1 cyrillic character
 */
abstract class LatinCyrillicAlphabetRepoI {

    protected abstract val latinToCyrillicAlphabet: Map<String, String>

    private val cyrillicToLatinAlphabet by lazy {
        latinToCyrillicAlphabet.entries.associate { it.value to it.key }
    }

    fun getLatinToCyrillicMap() = HashMap(latinToCyrillicAlphabet)
    fun getCyrillicToLatinMap() = HashMap(cyrillicToLatinAlphabet)
}
