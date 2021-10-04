package com.michaeltroger.serbianlatintocyrillic.macedonian.iso9


import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepoI
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianIso9AlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MacedonianIso9AlphabetRepoTest {

    private lateinit var repo: LatinCyrillicAlphabetRepoI

    @BeforeEach
    fun setUp() {
        repo = MacedonianIso9AlphabetRepo()
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
        assertThat(max).isEqualTo(2) // latin 2 letters - as some not part of unicode
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