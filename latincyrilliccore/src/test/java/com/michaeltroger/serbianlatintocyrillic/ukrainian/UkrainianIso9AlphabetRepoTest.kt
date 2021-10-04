package com.michaeltroger.serbianlatintocyrillic.ukrainian


import com.michaeltroger.serbianlatintocyrillic.LatinCyrillicAlphabetRepoI
import com.michaeltroger.serbianlatintocyrillic.repo.BulgarianIso9AlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.MacedonianIso9AlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.RussianIso9AlphabetRepo
import com.michaeltroger.serbianlatintocyrillic.repo.UkrainianIso9AlphabetRepo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UkrainianIso9AlphabetRepoTest {

    private lateinit var repo: LatinCyrillicAlphabetRepoI

    @BeforeEach
    fun setUp() {
        repo = UkrainianIso9AlphabetRepo()
    }

    @Test
    fun `Verify alphabet size`() {
        assertThat(repo.getCyrillicToLatinMap()).hasSize(65)
        assertThat(repo.getLatinToCyrillicMap()).hasSize(65)
    }

    @Test
    fun `Verify max latin character`() {
        var max = 0
        repo.getLatinToCyrillicMap().keys.forEach {
            if (it.length > max) {
                max = it.length
            }
        }
        assertThat(max).isEqualTo(2) // 2 letters in latin - not in unicode
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