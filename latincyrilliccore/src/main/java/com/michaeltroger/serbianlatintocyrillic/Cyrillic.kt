package com.michaeltroger.serbianlatintocyrillic

public interface Cyrillic {

    public suspend fun latinToCyrillic(text: String): String
    public suspend fun cyrillicToLatin(text: String): String
    public suspend fun isCyrillic(text: String): Boolean
    public val latinAlphabet: List<String>
    public val cyrillicAlphabet: List<String>
}
