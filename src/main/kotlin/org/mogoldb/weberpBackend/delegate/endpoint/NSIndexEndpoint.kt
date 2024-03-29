package org.mogoldb.weberpBackend.delegate.endpoint

import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.service.NSService
import org.springframework.web.bind.annotation.GetMapping

interface NSIndexEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @GetMapping
    open fun index(): List<OB> = service.findAll()
}