package com.michaeltroger.serbianlatintocyrillic

interface LatinToCyrillicI {

    suspend fun latinToCyrillic(text: String): String
    suspend fun cyrillicToLatin(text: String): String
    suspend fun isCyrillic(text: String): Boolean
    val getLatinAlphabet: List<String>
    val getCyrillicAlphabet: List<String>
}
