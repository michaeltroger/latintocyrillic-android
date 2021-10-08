package com.michaeltroger.serbianlatintocyrillic.bulgarian

import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.BulgarianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BulgarianCyrillicToLatinTest {

    private lateinit var converter: LatinToCyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = LatinToCyrillicImpl(BulgarianIso9AlphabetRepo())
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
    fun `Test alphabet`() { // verified with: https://www.translitteration.com/transliteration/en/bulgarian/iso-9/
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
А,а
Б,б
В,в
Г,г
Д,д
Е,е
Ж,ж
З,з
И,и
Й,й
К,к
Л,л
М,м
Н,н
О,о
П.п
Р,р
С,с
Т,т
У,у
Ф,ф
Х,х
Ц,ц
Ч,ч
Ш,ш
Щ,щ
ъ
ь
Ю,ю
Я,я
"""
            )).isEqualTo(
                    """"
A,a
B,b
V,v
G,g
D,d
E,e
Ž,ž
Z,z
I,i
J,j
K,k
L,l
M,m
N,n
O,o
P.p
R,r
S,s
T,t
U,u
F,f
H,h
C,c
Č,č
Š,š
Ŝ,ŝ
ʺ
ʹ
Û,û
Â,â
"""
            )
        }
    }

    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/bulgarian/iso-9/
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
аaбbвvгgдdеeжžзzиiйjкkлlмmнnоoпpрrсsтtуuфfхhцcчčшšщŝюûяâ
ъʺ
ьʹ
АAБBВVГGДDЕEЖŽЗZИIЙJКKЛLМMНNОOПPРRСSТTУUФFХHЦCЧČШŠЩŜЮÛЯÂ
"""
            )).isEqualTo(
                    """"
aabbvvggddeežžzziijjkkllmmnnoopprrssttuuffhhccččššŝŝûûââ
ʺʺ
ʹʹ
AABBVVGGDDEEŽŽZZIIJJKKLLMMNNOOPPRRSSTTUUFFHHCCČČŠŠŜŜÛÛÂÂ
"""
            )
        }
    }



}