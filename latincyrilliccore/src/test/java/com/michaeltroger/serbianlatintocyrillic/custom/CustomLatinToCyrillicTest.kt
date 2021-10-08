package com.michaeltroger.serbianlatintocyrillic.custom

import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.repo.CustomAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class CustomLatinToCyrillicTest {

    @Test
    fun `Test edge cases`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.latinToCyrillic("")).isEqualTo("")
            assertThat(converter.latinToCyrillic(".")).isEqualTo(".")
            assertThat(converter.latinToCyrillic(",")).isEqualTo(",")
            assertThat(converter.latinToCyrillic("!")).isEqualTo("!")
            assertThat(converter.latinToCyrillic("?")).isEqualTo("?")
        }
    }

    @Test
    fun `Test illegal input latin shorter`() {
        assertThrows<IllegalArgumentException> {
            CyrillicImpl(latin = listOf("a"), cyrillic = listOf('Љ', 'Џ'))
        }
    }

    @Test
    fun `Test illegal input cyrillic shorter`() {
        assertThrows<IllegalArgumentException> {
            CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ'))
        }
    }

    @Test
    fun `Test illegal input too many latin chars`() {
        val converter = CyrillicImpl(latin = listOf("abc", "d"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThrows<IllegalArgumentException> {
                converter.latinToCyrillic("test")
            }
            assertThrows<IllegalArgumentException> {
                converter.cyrillicToLatin("test")
            }
        }
    }

    @Test
    fun `Test with two letters in alphabet`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.latinToCyrillic("a")).isEqualTo("Љ")
            assertThat(converter.latinToCyrillic("bc")).isEqualTo("Џ")
            assertThat(converter.latinToCyrillic("b")).isEqualTo("b")
            assertThat(converter.latinToCyrillic("c")).isEqualTo("c")
        }
    }

    @Test
    fun `Test with one letter in alphabet`() {
        val converter = CyrillicImpl(latin = listOf("a"), cyrillic = listOf('Љ'))
        runBlocking {
            assertThat(converter.latinToCyrillic("a")).isEqualTo("Љ")
            assertThat(converter.latinToCyrillic("n")).isEqualTo("n")
        }
    }

    @Test
    fun `Test sentences`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.latinToCyrillic("abc ;-) nice"))
                    .isEqualTo("ЉЏ ;-) nice")

            assertThat(converter.latinToCyrillic("aa aaa aaaaa b b. B b c bc"))
                    .isEqualTo("ЉЉ ЉЉЉ ЉЉЉЉЉ b b. B b c Џ")
        }
    }

    @Test
    fun `Test extending serbian alphabet by one letter`() {
        val serbianConverter = CyrillicImpl(Alphabet.Serbian)
        runBlocking {
            val latin = serbianConverter.getLatinAlphabet()
            val cyrillic = serbianConverter.getCyrillicAlphabet()

            val converter = CyrillicImpl(latin = latin + listOf("Dz"), cyrillic = cyrillic.map { it.toCharArray()[0] } + listOf('Ѕ'))
            assertThat(converter.latinToCyrillic(
                    """"
A, a
B, b
V, v
G, g
D, d
Đ, đ
E, e
Ž, ž
Z, z
I, i
J, j
K, k
L, l
Lj, lj
M, m
N, n
Nj, nj
O, o
P, p
R, r
S, s
T, t
Ć, ć
U, u
F, f
H, h
C, c
Č, č
Dž, dž
Š, š
Dz
"""
            )).isEqualTo(
                    """"
А, а
Б, б
В, в
Г, г
Д, д
Ђ, ђ
Е, е
Ж, ж
З, з
И, и
Ј, ј
К, к
Л, л
Љ, љ
М, м
Н, н
Њ, њ
О, о
П, п
Р, р
С, с
Т, т
Ћ, ћ
У, у
Ф, ф
Х, х
Ц, ц
Ч, ч
Џ, џ
Ш, ш
Ѕ
"""
            )
        }
    }


}