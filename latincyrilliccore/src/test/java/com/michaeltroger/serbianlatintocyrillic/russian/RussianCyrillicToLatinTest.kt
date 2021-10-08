package com.michaeltroger.serbianlatintocyrillic.russian

import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.RussianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RussianCyrillicToLatinTest {

    private lateinit var converter: CyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = CyrillicImpl(RussianIso9AlphabetRepo())
    }

    @Test
    fun `Test edge cases`() {
        runBlocking {
            assertThat(converter.cyrillicToLatin("")).isEqualTo("")
            assertThat(converter.cyrillicToLatin(".")).isEqualTo(".")
            assertThat(converter.cyrillicToLatin(",")).isEqualTo(",")
            assertThat(converter.cyrillicToLatin("!")).isEqualTo("!")
            assertThat(converter.cyrillicToLatin("?")).isEqualTo("?")
        }
    }

    @Test
    fun `Test alphabet`() { // verified with: https://www.translitteration.com/transliteration/en/russian/iso-9/
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
Аа
Бб
Вв
Гг
Дд
Ее
Ёё
Жж
Зз
Ии
Йй
Кк
Лл
Мм
Нн
Оо
Пп
Рр
Сс
Тт
Уу
Фф
Хх
Цц
Чч
Шш
Щщ
ъ
ы
ь
Ээ
Юю
Яя
"""
            )).isEqualTo(
                    """"
Aa
Bb
Vv
Gg
Dd
Ee
Ëë
Žž
Zz
Ii
Jj
Kk
Ll
Mm
Nn
Oo
Pp
Rr
Ss
Tt
Uu
Ff
Hh
Cc
Čč
Šš
Ŝŝ
ʺ
y
ʹ
Èè
Ûû
Ââ
"""
            )
        }
    }

    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/russian/iso-9/
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
аaбbвvгgдdеeёëжžзzиiйjкkлlмmнnоoпpрrсsтtуuфfхhцcчčшšщŝыyюûяâъʺьʹэè
АAБBВVГGДDЕEЁËЖŽЗZИIЙJКKЛLМMНNОOПPРRСSТTУUФFХHЦCЧČШŠЩŜYЮÛЯÂЭÈ
"""
            )).isEqualTo(
                    """"
aabbvvggddeeëëžžzziijjkkllmmnnoopprrssttuuffhhccččššŝŝyyûûââʺʺʹʹèè
AABBVVGGDDEEËËŽŽZZIIJJKKLLMMNNOOPPRRSSTTUUFFHHCCČČŠŠŜŜYÛÛÂÂÈÈ
"""
            )
        }
    }



}