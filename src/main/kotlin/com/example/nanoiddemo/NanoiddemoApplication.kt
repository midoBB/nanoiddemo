package com.example.nanoiddemo

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class NanoiddemoApplication

fun main(args: Array<String>) {
    runApplication<NanoiddemoApplication>(*args)
}
//Create a commandLineRunner
@Component
class StartupRunner(val repository: LongIDEntityRepository) : CommandLineRunner {
    override fun run(args: Array<String>) {
        repository.save(LongIDEntity(name = "Nano"))
        repository.save(LongIDEntity(name = "Pico"))
        repository.save(LongIDEntity(name = "Femto"))
    }
}
@Component
class StartupRunner2(val repository: NanoIDEntityRepository) : CommandLineRunner {
    override fun run(args: Array<String>) {
        repository.save(NanoIDEntity(name = "Nano"))
        repository.save(NanoIDEntity(name = "Pico"))
        repository.save(NanoIDEntity(name = "Femto"))
    }
}
