package org.mongoldb.weberp.delegate.endpoint

import jakarta.validation.Valid
import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.service.NSService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface NSStoreEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @PostMapping
    open fun store(@Valid @RequestBody body: OB): ResponseEntity<OB> {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(service.create(body))
    }
}