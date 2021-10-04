package com.michaeltroger.serbianlatintocyrillic.ukrainian

import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillic
import com.michaeltroger.serbianlatintocyrillic.repo.BelarusianIso9AlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.RussianIso9AlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.UkrainianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UkrainianLatinToCyrillicTest {

    private lateinit var converter: LatinToCyrillic

    @BeforeEach
    fun setUp() {
        converter = LatinToCyrillic(UkrainianIso9AlphabetRepo())
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
A a	B b	V v	G g	G̀ g̀	D d	E e	Ê ê	Ž ž	Z z	I i
Ì ì	Ï ï	J j	K k	L l	M m	N n	O o	P p	R r	S s
T t	U u	F f	H h	C c	Č č	Š š	Ŝ ŝ	 ʹ	Û û	Â â
"""
            )).isEqualTo(
                    """"
А а	Б б	В в	Г г	Ґ ґ	Д д	Е е	Є є	Ж ж	З з	И и
І і	Ї ї	Й й	К к	Л л	М м	Н н	О о	П п	Р р	С с
Т т	У у	Ф ф	Х х	Ц ц	Ч ч	Ш ш	Щ щ	 ь	Ю ю	Я я
"""
            )
        }
    }

    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/russian/iso-9/
        runBlocking {
            assertThat(converter.latinToCyrillic(
                    """"
аaбbвvгgґg̀дdеeєêжžзzиiіìїïйjкkлlмmнnоoпpрrсsтtуuфfхhцcчčшšщŝюûяâьʹ’’
АAБBВVГGҐG̀ДDЕEЄÊЖŽЗZИIІÌЇÏЙJКKЛLМMНNОOПPРRСSТTУUФFХHЦCЧČШŠЩŜЮÛЯÂ
′’’
"""
            )).isEqualTo(
                    """"
ааббввггґґддееєєжжззииііїїййккллммннооппррссттууффххццччшшщщююяяьь’’
ААББВВГГҐҐДДЕЕЄЄЖЖЗЗИИІІЇЇЙЙККЛЛММННООППРРССТТУУФФХХЦЦЧЧШШЩЩЮЮЯЯ
′’’
"""
            )
        }
    }



}