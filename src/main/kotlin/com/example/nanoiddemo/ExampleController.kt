package com.example.nanoiddemo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController(
    val repository: LongIDEntityRepository,
    val repository2: NanoIDEntityRepository,
    val hashIDProvider: HashIDProvider
) {
    @GetMapping("/long")
    fun long(): List<LongIDEntity> {
        return repository.findAll()
    }
    @GetMapping("/nano")
    fun nano(): List<NanoIDEntity> {
        return repository2.findAll()
    }
    @GetMapping("/hash")
    fun hash() = repository.findAll().map { HashIDEntity(hashIDProvider.getHashID(it.id!!),it.name!!) }
}
