package org.mogoldb.weberpBackend.delegate.crud

import org.mogoldb.weberpBackend.delegate.DefaultEntity
import org.mogoldb.weberpBackend.delegate.DefaultService
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.payload.response.DeleteResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface DeleteControllerCrud<OB : DefaultEntity, PK : Long> {
    val service: DefaultService<OB, PK>

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