package com.michaeltroger.serbianlatintocyrillic.russian

import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.RussianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RussianLatinToCyrillicTest {

    private lateinit var converter: CyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = CyrillicImpl(RussianIso9AlphabetRepo())
    }

    @Test
    fun `Test edge cases`() {
        runBlocking {
            assertThat(converter.latinToCyrillic("")).isEqualTo("")
            assertThat(converter.latinToCyrillic(".")).isEqualTo(".")
            assertThat(converter.latinToCyrillic(",")).isEqualTo(",")
            assertThat(converter.latinToCyrillic("!")).isEqualTo("!")
            assertThat(converter.latinToCyrillic("?")).isEqualTo("?")
        }
    }

    @Test
    fun `Test alphabet`() { // verified with: https://www.translitteration.com/transliteration/en/russian/iso-9/
        runBlocking {
            assertThat(converter.latinToCyrillic(
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
            )).isEqualTo(
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
            )
        }
    }

    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/russian/iso-9/
        runBlocking {
            assertThat(converter.latinToCyrillic(
                    """"
aabbvvggddeeëëžžzziijjkkll
mmnnoopprrssttuuff
hhccččššŝŝ
yyûûââ
ʺʺʹʹèè
AABBVVGGDDEEËËŽŽZZIIJJKKLL
MMNNOOPPRRSSTTUUFF
HHCCČČŠŠŜŜÛÛÂÂ
ÈÈ
ʺ ʹ
"""
            )).isEqualTo(
                    """"
ааббввггддееёёжжззииййкклл
ммннооппррссттууфф
ххццччшшщщ
ыыююяя
ъъььээ
ААББВВГГДДЕЕЁЁЖЖЗЗИИЙЙККЛЛ
ММННООППРРССТТУУФФ
ХХЦЦЧЧШШЩЩЮЮЯЯ
ЭЭ
ъ ь
"""
            )
        }
    }



}