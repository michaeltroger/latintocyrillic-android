package com.michaeltroger.serbianlatintocyrillic

public object CyrillicFactory {

    public fun createConverter(alphabet: Alphabet): Cyrillic = CyrillicImpl(alphabet)
    public fun createConverter(latin: List<String>, cyrillic: List<String>): Cyrillic = CyrillicImpl(latin, cyrillic)
}