package com.michaeltroger.serbianlatintocyrillic.repo

import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepoI


class MacedonianAlphabetRepo : LatinCyrillicAlphabetRepoI() {

    override val latinToCyrillicAlphabet by lazy {
        hashMapOf(
                "A" to "А",
                "B" to "Б",
                "V" to "В",
                "G" to "Г",
                "D" to "Д",
                "Ǵ" to "Ѓ",
                "E" to "Е",
                "Ž" to "Ж",
                "Z" to "З",
                "Dz" to "Ѕ",
                "I" to "И",
                "J" to "Ј",
                "K" to "К",
                "L" to "Л",
                "Lj" to "Љ",
                "M" to "М",
                "N" to "Н",
                "Nj" to "Њ",
                "O" to "О",
                "P" to "П",
                "R" to "Р",
                "S" to "С",
                "T" to "Т",
                "Ḱ" to "Ќ",
                "U" to "У",
                "F" to "Ф",
                "H" to "Х",
                "C" to "Ц",
                "Č" to "Ч",
                "Dž" to "Џ",
                "Š" to "Ш",
                "a" to "а",
                "b" to "б",
                "v" to "в",
                "g" to "г",
                "d" to "д",
                "ǵ" to "ѓ",
                "e" to "е",
                "ž" to "ж",
                "z" to "з",
                "dz" to "ѕ",
                "i" to "и",
                "j" to "ј",
                "k" to "к",
                "l" to "л",
                "lj" to "љ",
                "m" to "м",
                "n" to "н",
                "nj" to "њ",
                "o" to "о",
                "p" to "п",
                "r" to "р",
                "s" to "с",
                "t" to "т",
                "ḱ" to "ќ",
                "u" to "у",
                "f" to "ф",
                "h" to "х",
                "c" to "ц",
                "č" to "ч",
                "dž" to "џ",
                "š" to "ш"
        )
    }
}
