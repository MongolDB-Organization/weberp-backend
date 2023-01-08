package org.mogoldb.weberpBackend.delegate.crud

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import org.mogoldb.weberpBackend.delegate.DefaultService
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

interface UpdateControllerCrud<OB : DefaultEntity, PK : Long> {
    val service: DefaultService<OB, PK>

    @PutMapping("/{id}")
    open fun update(@Valid @RequestBody body: OB, @PathVariable(name = "id") id: PK): ResponseEntity<OB> {
        val existsPredicate = service.findById(id)
            ?: throw NotFoundException()
        return ResponseEntity.ok(service.save(body, id))
    }
}