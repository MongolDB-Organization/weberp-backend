package org.mogoldb.weberpBackend.delegate.endpoint

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.service.NSService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface NSStoreEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @PostMapping
    open fun store(@Valid @RequestBody body: OB): ResponseEntity<OB> {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(service.save(body, null))
    }
}