package com.michaeltroger.serbianlatintocyrillic.belarusian


import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.BelarusianIso9AlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BelarusianIso9AlphabetRepoTest {

    private lateinit var repo: LatinCyrillicAlphabetRepo

    @BeforeEach
    fun setUp() {
        repo = BelarusianIso9AlphabetRepo()
    }

    @Test
    fun `Verify alphabet size`() {
        assertThat(repo.getCyrillicToLatinMap()).hasSize(62)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(62)
    }

    @Test
    fun `Verify max latin character`() {
        var max = 0
        repo.getLatinToCyrillicMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(1)
    }

    @Test
    fun `Verify max cyrillic character`() {
        var max = 0
        repo.getCyrillicToLatinMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(1)
    }
}