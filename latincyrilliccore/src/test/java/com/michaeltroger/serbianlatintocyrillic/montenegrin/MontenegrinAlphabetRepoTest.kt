package com.michaeltroger.serbianlatintocyrillic.montenegrin


import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.MontenegrinAlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class MontenegrinAlphabetRepoTest {

    private lateinit var repo: LatinCyrillicAlphabetRepo

    @BeforeEach
    fun setUp() {
        repo = MontenegrinAlphabetRepo()
    }

    @Test
    fun `Verify alphabet size`() {
        assertThat(repo.getCyrillicToLatinMap()).hasSize(64)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(64)
    }

    @Test
    fun `Verify max latin character`() {
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
        var max = 0
        repo.getCyrillicToLatinMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(2)
    }
}