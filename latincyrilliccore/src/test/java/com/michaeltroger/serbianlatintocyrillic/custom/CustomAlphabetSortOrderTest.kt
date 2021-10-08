package com.michaeltroger.serbianlatintocyrillic.custom

import com.michaeltroger.serbianlatintocyrillic.Alphabet
import com.michaeltroger.serbianlatintocyrillic.repo.CustomAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.CyrillicImpl
import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CustomAlphabetSortOrderTest {

    @Test
    fun `Test sorted latin`() {
        val converter = CyrillicImpl(latin = listOf("x", "c", "d", "b"), cyrillic = listOf('Ћ', 'Љ', 'П', 'Џ'))
        runBlocking {
            assertThat(converter.latinAlphabet).isEqualTo(
                listOf("b", "c", "d", "x")
            )
            assertThat(converter.cyrillicAlphabet).isEqualTo(
                listOf("Џ", "Љ", "П", "Ћ")
            )
        }
    }

}