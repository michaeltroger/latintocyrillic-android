package com.michaeltroger.serbianlatintocyrillic.serbian

import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SerbianAlphabetRepoTest {

    private lateinit var repo: LatinCyrillicAlphabetRepo

    @BeforeEach
    fun setUp() {
        repo = SerbianAlphabetRepo()
    }

    @Test
    fun `Verify alphabet size`() {
        assertThat(repo.getCyrillicToLatinMap()).hasSize(60)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(60)
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
        assertThat(max).isEqualTo(1)
    }
}