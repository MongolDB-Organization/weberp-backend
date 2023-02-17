package org.mongoldb.weberp.delegate.endpoint

import jakarta.validation.Valid
import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.service.NSService
import org.mongoldb.weberp.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

interface NSUpdateEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @PutMapping("/{id}")
    open fun update(@Valid @RequestBody body: OB, @PathVariable(name = "id") id: Long): ResponseEntity<OB> {
        service.findById(id) ?: throw NotFoundException()
        return ResponseEntity.ok(service.update(body, id))
    }
}