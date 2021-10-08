package com.michaeltroger.serbianlatintocyrillic.serbian

import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SerbianAutoConvertTest {

    private lateinit var converter: CyrillicImpl

    @BeforeEach
    fun setUp() {
        converter = CyrillicImpl(Alphabet.Serbian)
    }

    @Test
    fun `Test edge cases`() {
        runBlocking {
            assertThat(converter.isCyrillic("")).isFalse()
            assertThat(converter.isCyrillic(".")).isFalse()
            assertThat(converter.isCyrillic(",")).isFalse()
            assertThat(converter.isCyrillic("!")).isFalse()
            assertThat(converter.isCyrillic("?")).isFalse()
        }
    }

    @Test
    fun `Verify latin to cyrillic`() {
        runBlocking {
            assertThat(converter.isCyrillic("Hello")).isFalse()
            assertThat(converter.isCyrillic(
                    "Procenjena vrednost fabrike je veća od 514 miliona dinara (4.373.000 evra), a kako je ponuda manja od 50 odsto procenjene vrednosti, saglasnost za prodaju dao je odbor poverilaca."))
                    .isFalse()
            assertThat(converter.isCyrillic(".,!Hello")).isFalse()

        }
    }

    @Test
    fun `Verify cyrillic to latin`() {
        runBlocking {
            assertThat(converter.isCyrillic("Хелло")).isTrue()
            assertThat(converter.isCyrillic(
                    "Процењена вредност фабрике је већа од 514 милиона динара (4.373.000 евра), а како је понуда мања од 50 одсто процењене вредности, сагласност за продају дао је одбор поверилаца."))
                    .isTrue()
            assertThat(converter.isCyrillic(".,!Хелло")).isTrue()
        }
    }
}