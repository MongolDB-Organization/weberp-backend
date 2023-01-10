package org.mogoldb.weberpBackend.delegate.endpoint

import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.service.NSService
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.payload.response.DeleteResponse
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface NSDeleteEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @DeleteMapping("/{id}")
    open fun destroy(@PathVariable(name = "id") id: Long): ResponseEntity<DeleteResponse> {
        try {
            service.deleteById(id)
            return ResponseEntity.ok().body(DeleteResponse())
        } catch (e: EmptyResultDataAccessException) {
            throw NotFoundException()
        }
    }
}