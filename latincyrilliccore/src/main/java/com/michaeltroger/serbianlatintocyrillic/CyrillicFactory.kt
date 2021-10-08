package com.michaeltroger.serbianlatintocyrillic

public object CyrillicFactory {

    public fun createConverter(alphabet: Alphabet): LatinToCyrillic = LatinToCyrillicImpl(alphabet)
    public fun createConverter(latin: List<String>, cyrillic: List<String>): LatinToCyrillic = LatinToCyrillicImpl(latin, cyrillic)
}