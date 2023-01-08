package org.mogoldb.weberpBackend.delegate.crud

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import org.mogoldb.weberpBackend.delegate.DefaultService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface PostControllerCrud<OB : DefaultEntity, PK : Long> {
    val service: DefaultService<OB, PK>

    @PostMapping
    open fun store(@Valid @RequestBody body: OB): ResponseEntity<OB> {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(service.save(body, null))
    }
}