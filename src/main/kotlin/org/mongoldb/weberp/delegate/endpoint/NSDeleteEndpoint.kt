package org.mongoldb.weberp.delegate.endpoint

import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.service.NSService
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.dto.response.DeleteResponseDTO
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

interface NSDeleteEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @DeleteMapping("/{id}")
    open fun destroy(@PathVariable(name = "id") id: Long): ResponseEntity<DeleteResponseDTO> {
        try {
            service.deleteById(id)
            return ResponseEntity.ok().body(DeleteResponseDTO())
        } catch (e: EmptyResultDataAccessException) {
            throw NotFoundException()
        }
    }
}