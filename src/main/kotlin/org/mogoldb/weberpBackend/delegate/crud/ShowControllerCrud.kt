package org.mogoldb.weberpBackend.delegate.crud

import org.mogoldb.weberpBackend.delegate.DefaultEntity
import org.mogoldb.weberpBackend.delegate.DefaultService
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

interface ShowControllerCrud<OB : DefaultEntity, PK : Long> {
    val service: DefaultService<OB, PK>

    @GetMapping("/{id}")
    open fun show(@PathVariable(name = "id") id: PK): ResponseEntity<OB> {
        val existsPredicate = service.findById(id) ?: throw NotFoundException()
        return ResponseEntity.ok(existsPredicate)
    }

}