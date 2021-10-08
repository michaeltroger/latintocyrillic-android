package com.michaeltroger.serbianlatintocyrillic.macedonian.academic

import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MacedonianLatinToCyrillicTest {

    private lateinit var converter: CyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = CyrillicImpl(Alphabet.Macedonian)
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
    fun `Test double letters`() {
        runBlocking {
            assertThat(converter.latinToCyrillic("Nj")).isEqualTo("Њ")
            assertThat(converter.latinToCyrillic("nj")).isEqualTo("њ")
            assertThat(converter.latinToCyrillic("Lj")).isEqualTo("Љ")
            assertThat(converter.latinToCyrillic("lj")).isEqualTo("љ")
            assertThat(converter.latinToCyrillic("Dž")).isEqualTo("Џ")
            assertThat(converter.latinToCyrillic("dž")).isEqualTo("џ")
            assertThat(converter.latinToCyrillic("Dz")).isEqualTo("Ѕ")
            assertThat(converter.latinToCyrillic("dz")).isEqualTo("ѕ")
        }
    }

    @Test
    fun `Test single letters`() {
        runBlocking {
            assertThat(converter.latinToCyrillic("N")).isEqualTo("Н")
            assertThat(converter.latinToCyrillic("n")).isEqualTo("н")
            assertThat(converter.latinToCyrillic("L")).isEqualTo("Л")
            assertThat(converter.latinToCyrillic("l")).isEqualTo("л")
            assertThat(converter.latinToCyrillic("D")).isEqualTo("Д")
            assertThat(converter.latinToCyrillic("d")).isEqualTo("д")
            assertThat(converter.latinToCyrillic("Ǵ")).isEqualTo("Ѓ")
            assertThat(converter.latinToCyrillic("ǵ")).isEqualTo("ѓ")
            assertThat(converter.latinToCyrillic("Ḱ")).isEqualTo("Ќ")
            assertThat(converter.latinToCyrillic("ḱ")).isEqualTo("ќ")
        }
    }

    @Test
    fun `Test sentences`() {
        runBlocking { // source: https://vecer.mk and https://translate.google.com/
            assertThat(converter.latinToCyrillic(
                    """Očigledno, i Amerikancite zabeležale deka Bjelo Dugme e poveḱe od muzika."""
            )).isEqualTo(
                    """Очигледно, и Американците забележале дека Бјело Дугме е повеќе од музика.""")

            assertThat(converter.latinToCyrillic(
                    """"Bjelo Dugme, kako i mnogu drugi izvori na kulturata i subkulturata na Jugoslavija, doaǵa od Saraevo.
|Gradot koj e dom na raskažuvačite Zuko Džumhur i Abdulah Sidran, se ušte najgolemiot jugoslovenski umetnik na filmot Emir Kustirica,
|muzičkite legendi Zdravko Čoliḱ, Zabranjeno pušenje i Doktor Nele Karajliḱ, Duško Trifunoviḱ, slikarot Mersad Berber,
|televiziskiot fenomen Top lista nadrealista... Saraevo, gradot na sevdahot, muzikata i umetnicite, najubavata mešavina na četiri veri i religii vo Evropa,
|so najmnogu jugoslovenski kopnež i koga Jugoslavija ja imaše, i koga potoa beše izneverena tokmu od verskite vodači i uličnite dileri, vo sojuz."""
            )).isEqualTo(
                    """"Бјело Дугме, како и многу други извори на културата и субкултурата на Југославија, доаѓа од Сараево.
|Градот кој е дом на раскажувачите Зуко Џумхур и Абдулах Сидран, се уште најголемиот југословенски уметник на филмот Емир Кустирица,
|музичките легенди Здравко Чолиќ, Забрањено пушење и Доктор Неле Карајлиќ, Душко Трифуновиќ, сликарот Мерсад Бербер,
|телевизискиот феномен Топ листа надреалиста... Сараево, градот на севдахот, музиката и уметниците, најубавата мешавина на четири вери и религии во Европа,
|со најмногу југословенски копнеж и кога Југославија ја имаше, и кога потоа беше изневерена токму од верските водачи и уличните дилери, во сојуз."""
                )
        }
    }

    @Test // verified with: https://www.lexilogos.com and https://transliteration.eki.ee and https://www.loc.gov/catdir/cpso/roman.html
    fun `Test wiki alphabet`() { // source: https://mk.wikipedia.org/wiki/%D0%A2%D1%80%D0%B0%D0%BD%D1%81%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%98%D0%B0_%D0%BD%D0%B0_%D0%BC%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%BE%D1%82%D0%BE_%D0%BF%D0%B8%D1%81%D0%BC%D0%BE
        runBlocking {
            assertThat(converter.latinToCyrillic(
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
            )).isEqualTo(
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

            )
        }
    }

    @Test // verified with: https://www.lexilogos.com and https://transliteration.eki.ee and https://www.loc.gov/catdir/cpso/roman.html
    fun `Test ALA-LC alphabet`() { // source: https://mk.wikipedia.org/wiki/%D0%A2%D1%80%D0%B0%D0%BD%D1%81%D0%BB%D0%B8%D1%82%D0%B5%D1%80%D0%B0%D1%86%D0%B8%D1%98%D0%B0_%D0%BD%D0%B0_%D0%BC%D0%B0%D0%BA%D0%B5%D0%B4%D0%BE%D0%BD%D1%81%D0%BA%D0%BE%D1%82%D0%BE_%D0%BF%D0%B8%D1%81%D0%BC%D0%BE
        runBlocking {
            assertThat(converter.latinToCyrillic(

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
            )).isEqualTo(
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
            )
        }
    }

}