package com.michaeltroger.serbianlatintocyrillic

import com.michaeltroger.serbianlatintocyrillic.repo.SerbianAlphabetRepo
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class SpeedTest {

    @Test
    fun `Test sentences`() {
        runBlocking {
            val time = System.nanoTime()
            val converter = LatinToCyrillicImpl(SerbianAlphabetRepo())
            converter.latinToCyrillic("Procenjena vrednost fabrike je veća od 514 miliona dinara (4.373.000 evra), a kako je ponuda manja od 50 odsto procenjene vrednosti, saglasnost za prodaju dao je odbor poverilaca.")
            val computationTime = System.nanoTime() - time

            println(TimeUnit.MILLISECONDS.convert(computationTime, TimeUnit.NANOSECONDS))
        }
    }

}