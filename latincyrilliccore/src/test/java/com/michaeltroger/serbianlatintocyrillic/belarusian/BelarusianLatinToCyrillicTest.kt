package com.michaeltroger.serbianlatintocyrillic.belarusian

import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.BelarusianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BelarusianLatinToCyrillicTest {

    private lateinit var converter: LatinToCyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = LatinToCyrillicImpl(BelarusianIso9AlphabetRepo())
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
    fun `Test alphabet`() { // verified with: https://www.translitteration.com/transliteration/en/belarusian/iso-9/
        runBlocking {
            assertThat(converter.latinToCyrillic(
                    """"
A   a
B   b
V   v
G   g
D   d
E   e
Ë   ë
Ž   ž
Z   z
Ì   ì
J   j
K   k
L   l
M   m
N   n
O   o
P   p
R   r
S   s
T   t
U   u
Ŭ   ŭ
F   f
H   h
C   c
Č   č
Š   š
   y
   ʹ
È   è
Û   û
Â   â
ʼ
"""
            )).isEqualTo(
                    """"
А   а
Б   б
В   в
Г   г
Д   д
Е   е
Ё   ё
Ж   ж
З   з
І   і
Й   й
К   к
Л   л
М   м
Н   н
О   о
П   п
Р   р
С   с
Т   т
У   у
Ў   ў
Ф   ф
Х   х
Ц   ц
Ч   ч
Ш   ш
   ы
   ь
Э   э
Ю   ю
Я   я
ʼ
"""

            )
        }
    }


    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/belarusian/iso-9/ difference ǔ and ґ (not standard char), DŽ DZ
        runBlocking {
            assertThat(converter.latinToCyrillic(
                    """"
аaбbвvгgдdджdžдзdz
еeёëжžзzіìйjкkлl
мmнnоoпpрrсs
тtуuўŭф
fхhцcчčшšыyэèюûяâьʹ
АAБBВVГG
ДDДжDžДзDz
ЕEЁËЖŽЗZІÌЙJКKЛL
МMНNОOПPРRСSТTУUЎŬФ
FХHЦCЧČШŠЭÈЮÛЯÂ
"""
            )).isEqualTo(
                    """"
ааббввггдддждждздз
ееёёжжззііййкклл
ммннооппррсс
ттууўўф
фххццччшшыыээююяяьь
ААББВВГГ
ДДДжДжДзДз
ЕЕЁЁЖЖЗЗІІЙЙККЛЛ
ММННООППРРССТТУУЎЎФ
ФХХЦЦЧЧШШЭЭЮЮЯЯ
"""
            )
        }
    }



}