package com.michaeltroger.serbianlatintocyrillic

/**
 * Supports 1 or 2 latin script characters translated to 1 cyrillic character
 */
public abstract class LatinCyrillicAlphabetRepo {

    protected abstract val latinToCyrillicAlphabet: Map<String, String>

    private val cyrillicToLatinAlphabet by lazy {
        latinToCyrillicAlphabet.entries.associate { it.value to it.key }
    }

    public fun getLatinToCyrillicMap(): Map<String, String> = HashMap(latinToCyrillicAlphabet)
    public fun getCyrillicToLatinMap(): Map<String, String> = HashMap(cyrillicToLatinAlphabet)
}
