package org.mogoldb.weberpBackend.delegate

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.payload.response.DeleteResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

abstract class DefaultController<OB : DefaultEntity, PK : Long>(private val service: DefaultService<OB, PK>) {

    @GetMapping
    open fun index(): List<OB> =
        service.findAll()

    @GetMapping("/{id}")
    open fun show(@PathVariable(name = "id") id: PK): ResponseEntity<OB> {
        val existsPredicate = service.findById(id)
        if (!existsPredicate.isPresent)
            throw NotFoundException()
        return ResponseEntity.ok(existsPredicate.get())
    }

    @PostMapping
    open fun store(@Valid @RequestBody body: OB): ResponseEntity<OB> {
        return ResponseEntity.ok(service.save(body))
    }

    @PutMapping("/{id}")
    open fun update(@Valid @RequestBody body: OB, @PathVariable(name = "id") id: PK): ResponseEntity<OB> {
        val existsPredicate = service.findById(id)
        if (!existsPredicate.isPresent)
            throw NotFoundException()
        return ResponseEntity.ok(service.save(body, id))
    }

    @DeleteMapping("/{id}")
    open fun destroy(@PathVariable(name = "id") id: PK): ResponseEntity<DeleteResponse> {
        try {
            service.deleteById(id)
            return ResponseEntity.ok().body(DeleteResponse())
        } catch (e: EmptyResultDataAccessException) {
            throw NotFoundException()
        }
    }
}