package com.michaeltroger.serbianlatintocyrillic.custom

import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.repo.CustomAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CustomCyrillicToLatinTest {

    @Test
    fun `Test edge cases`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.cyrillicToLatin("")).isEqualTo("")
            assertThat(converter.cyrillicToLatin(".")).isEqualTo(".")
            assertThat(converter.cyrillicToLatin(",")).isEqualTo(",")
            assertThat(converter.cyrillicToLatin("!")).isEqualTo("!")
            assertThat(converter.cyrillicToLatin("?")).isEqualTo("?")
        }
    }

    @Test
    fun `Test with two letters in alphabet`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.cyrillicToLatin("Љ")).isEqualTo("a")
            assertThat(converter.cyrillicToLatin("Џ")).isEqualTo("bc")
            assertThat(converter.cyrillicToLatin("b")).isEqualTo("b")
            assertThat(converter.cyrillicToLatin("c")).isEqualTo("c")
        }
    }

    @Test
    fun `Test with one letter in alphabet`() {
        val converter = CyrillicImpl(latin = listOf("a"), cyrillic = listOf('Љ'))
        runBlocking {
            assertThat(converter.cyrillicToLatin("Љ")).isEqualTo("a")
            assertThat(converter.cyrillicToLatin("n")).isEqualTo("n")
        }
    }

    @Test
    fun `Test sentences`() {
        val converter = CyrillicImpl(latin = listOf("a", "bc"), cyrillic = listOf('Љ', 'Џ'))
        runBlocking {
            assertThat(converter.cyrillicToLatin("ЉЏ ;-) nice"))
                    .isEqualTo("abc ;-) nice")

            assertThat(converter.cyrillicToLatin("ЉЉ ЉЉЉ ЉЉЉЉЉ b b. B b c Џ"))
                    .isEqualTo("aa aaa aaaaa b b. B b c bc")
        }
    }

    @Test
    fun `Test extending serbian alphabet by one letter`() {
        val serbianConverter = CyrillicImpl(Alphabet.Serbian)
        runBlocking {
            val latin = serbianConverter.getLatinAlphabet()
            val cyrillic = serbianConverter.getCyrillicAlphabet()
            val converter = CyrillicImpl(latin = latin + listOf("Dz"), cyrillic = cyrillic.map { it.toCharArray()[0] } + listOf('Ѕ'))

            assertThat(converter.cyrillicToLatin(
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
            )).isEqualTo(
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
            )
        }
    }



}