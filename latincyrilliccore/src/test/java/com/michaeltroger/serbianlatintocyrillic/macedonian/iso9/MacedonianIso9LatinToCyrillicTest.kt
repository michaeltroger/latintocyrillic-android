package com.michaeltroger.serbianlatintocyrillic.macedonian.iso9

import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianIso9AlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MacedonianIso9LatinToCyrillicTest {

    private lateinit var converter: CyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = CyrillicImpl(MacedonianIso9AlphabetRepo())
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
    fun `Test alphabet`() { // verified with: https://www.translitteration.com/transliteration/en/macedonian/iso-9/
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
Ẑ ẑ
I i
J̌ ǰ
K k
L l
L̂ l̂
M m
N n
N̂ n̂
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
D̂ d̂
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

    @Test
    fun `Test alphabet from translitteration`() { // verified with: https://www.translitteration.com/transliteration/en/macedonian/iso-9/
        runBlocking {
            assertThat(converter.latinToCyrillic(
                    """"
аaбbвvгgдdѓǵеeжžзzѕẑиiјǰкkлlљl̂мmнnњn̂оoпpрrсsтtќḱуuфfхhцcчčџd̂шš
АAБBВVГGДDЃǴЕEЖŽЗZЅẐИIЈJ̌КKЛLЉL̂МMНNЊN̂ОOПPРRСSТTЌḰУUФFХHЦCЧČЏD̂ШŠ
"""
            )).isEqualTo(
                    """"
ааббввггддѓѓеежжззѕѕиијјккллљљммннњњооппррссттќќууффххццччџџшш
ААББВВГГДДЃЃЕЕЖЖЗЗЅЅИИЈЈККЛЛЉЉММННЊЊООППРРССТТЌЌУУФФХХЦЦЧЧЏЏШШ
"""
            )
        }
    }



}