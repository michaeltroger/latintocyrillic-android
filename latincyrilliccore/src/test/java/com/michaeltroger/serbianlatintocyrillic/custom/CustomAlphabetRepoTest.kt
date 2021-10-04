package com.michaeltroger.serbianlatintocyrillic.custom

import com.michaeltroger.serbianlatintocyrillic.repo.CustomAlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CustomAlphabetRepoTest {

    @Test
    fun `Verify alphabet size`() {
        val repo = CustomAlphabetRepo(latin = emptyList(), cyrillic = emptyList())
        assertThat(repo.getCyrillicToLatinMap()).hasSize(0)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(0)
    }

    @Test
    fun `Verify max latin character`() {
        val repo = CustomAlphabetRepo(latin = listOf("a", "bc"), cyrillic = listOf("Љ", "Џ"))
        assertThat(repo.getCyrillicToLatinMap()).hasSize(2)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(2)

        var max = 0
        repo.getLatinToCyrillicMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(2)
    }

    @Test
    fun `Verify max cyrillic character`() {
        val repo = CustomAlphabetRepo(latin = listOf("a", "bc"), cyrillic = listOf("Љ", "Џ"))
        var max = 0
        repo.getCyrillicToLatinMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(1)
    }

}