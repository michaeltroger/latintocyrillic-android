package com.michaeltroger.serbianlatintocyrillic.macedonian.academic

import com.michaeltroger.serbianlatintocyrillic.LatinToCyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MacedonianCyrillicToLatinTest {

    private lateinit var converter: LatinToCyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = LatinToCyrillicImpl(MacedonianAlphabetRepo())
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
    fun `Test double letters`() {
        runBlocking {
            assertThat(converter.cyrillicToLatin("Њ")).isEqualTo("Nj")
            assertThat(converter.cyrillicToLatin("њ")).isEqualTo("nj")
            assertThat(converter.cyrillicToLatin("Љ")).isEqualTo("Lj")
            assertThat(converter.cyrillicToLatin("љ")).isEqualTo("lj")
            assertThat(converter.cyrillicToLatin("Џ")).isEqualTo("Dž")
            assertThat(converter.cyrillicToLatin("џ")).isEqualTo("dž")
            assertThat(converter.cyrillicToLatin("Ѕ")).isEqualTo("Dz")
            assertThat(converter.cyrillicToLatin("ѕ")).isEqualTo("dz")
        }
    }

    @Test
    fun `Test single letters`() {
        runBlocking {
            assertThat(converter.cyrillicToLatin("Н")).isEqualTo("N")
            assertThat(converter.cyrillicToLatin("н")).isEqualTo("n")
            assertThat(converter.cyrillicToLatin("Л")).isEqualTo("L")
            assertThat(converter.cyrillicToLatin("л")).isEqualTo("l")
            assertThat(converter.cyrillicToLatin("Д")).isEqualTo("D")
            assertThat(converter.cyrillicToLatin("д")).isEqualTo("d")
            assertThat(converter.cyrillicToLatin("Ѓ")).isEqualTo("Ǵ")
            assertThat(converter.cyrillicToLatin("ѓ")).isEqualTo("ǵ")
            assertThat(converter.cyrillicToLatin("Ќ")).isEqualTo("Ḱ")
            assertThat(converter.cyrillicToLatin("ќ")).isEqualTo("ḱ")
        }
    }

    @Test
    fun `Test sentences`() {
        runBlocking { // source: https://vecer.mk and https://translate.google.com/
            assertThat(converter.cyrillicToLatin(
"""Очигледно, и Американците забележале дека Бјело Дугме е повеќе од музика."""
            )).isEqualTo(
"""Očigledno, i Amerikancite zabeležale deka Bjelo Dugme e poveḱe od muzika.""")

            assertThat(converter.cyrillicToLatin(
""""Бјело Дугме, како и многу други извори на културата и субкултурата на Југославија, доаѓа од Сараево.
|Градот кој е дом на раскажувачите Зуко Џумхур и Абдулах Сидран, се уште најголемиот југословенски уметник на филмот Емир Кустирица,
|музичките легенди Здравко Чолиќ, Забрањено пушење и Доктор Неле Карајлиќ, Душко Трифуновиќ, сликарот Мерсад Бербер,
|телевизискиот феномен Топ листа надреалиста... Сараево, градот на севдахот, музиката и уметниците, најубавата мешавина на четири вери и религии во Европа,
|со најмногу југословенски копнеж и кога Југославија ја имаше, и кога потоа беше изневерена токму од верските водачи и уличните дилери, во сојуз."""
            )).isEqualTo(
""""Bjelo Dugme, kako i mnogu drugi izvori na kulturata i subkulturata na Jugoslavija, doaǵa od Saraevo.
|Gradot koj e dom na raskažuvačite Zuko Džumhur i Abdulah Sidran, se ušte najgolemiot jugoslovenski umetnik na filmot Emir Kustirica,
|muzičkite legendi Zdravko Čoliḱ, Zabranjeno pušenje i Doktor Nele Karajliḱ, Duško Trifunoviḱ, slikarot Mersad Berber,
|televiziskiot fenomen Top lista nadrealista... Saraevo, gradot na sevdahot, muzikata i umetnicite, najubavata mešavina na četiri veri i religii vo Evropa,
|so najmnogu jugoslovenski kopnež i koga Jugoslavija ja imaše, i koga potoa beše izneverena tokmu od verskite vodači i uličnite dileri, vo sojuz."""
                )
        }
    }

    @Test // verified with: https://www.lexilogos.com and https://transliteration.eki.ee and https://www.loc.gov/catdir/cpso/roman.html
    fun `Test wiki alphabet`() { // source: https://mk.wikipedia.org/wiki/%D0%A2%D1%80%D0%B0%D0%BD%D1%81%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%98%D0%B0_%D0%BD%D0%B0_%D0%BC%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%BE%D1%82%D0%BE_%D0%BF%D0%B8%D1%81%D0%BC%D0%BE
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
А а
Б б
В в
Г г
Д д
Ѓ ѓ
Е е
Ж ж
З з
Ѕ ѕ
И и
Ј ј
К к
Л л
Љ љ
М м
Н н
Њ њ
О о
П п
Р р
С с
Т т
Ќ ќ
У у
Ф ф
Х х
Ц ц
Ч ч
Џ џ
Ш ш
"""
            )).isEqualTo(
                    """"
A a
B b
V v
G g
D d
Ǵ ǵ
E e
Ž ž
Z z
Dz dz
I i
J j
K k
L l
Lj lj
M m
N n
Nj nj
O o
P p
R r
S s
T t
Ḱ ḱ
U u
F f
H h
C c
Č č
Dž dž
Š š
"""
            )
        }
    }

    @Test // verified with: https://www.lexilogos.com and https://transliteration.eki.ee and https://www.loc.gov/catdir/cpso/roman.html
    fun `Test ALA-LC alphabet`() { // source: https://mk.wikipedia.org/wiki/%D0%A2%D1%80%D0%B0%D0%BD%D1%81%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%98%D0%B0_%D0%BD%D0%B0_%D0%BC%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%BE%D1%82%D0%BE_%D0%BF%D0%B8%D1%81%D0%BC%D0%BE
        runBlocking {
            assertThat(converter.cyrillicToLatin(
                    """"
А  а
Б  б
В  в
Г  г
Ѓ  ѓ
Д  д
Е  е
Ж  ж
З  з
Ѕ  ѕ
И  и
Ј  ј
К  к
Ќ  ќ
Л  л
Љ  љ
М  м
Н  н
Њ  њ
О  о
П  п
Р  р
С  с
Т  т
У  у
Ф  ф
Х  х
Ц  ц
Ч  ч
Џ  џ
Ш  ш
"""
            )).isEqualTo(
                    """"
A  a
B  b
V  v
G  g
Ǵ  ǵ
D  d
E  e
Ž  ž
Z  z
Dz  dz
I  i
J  j
K  k
Ḱ  ḱ
L  l
Lj  lj
M  m
N  n
Nj  nj
O  o
P  p
R  r
S  s
T  t
U  u
F  f
H  h
C  c
Č  č
Dž  dž
Š  š
"""
            )
        }
    }

}