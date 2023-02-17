package org.mongoldb.weberp.delegate.endpoint

import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.service.NSService
import org.springframework.web.bind.annotation.GetMapping

interface NSIndexEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @GetMapping
    open fun index(): List<OB> = service.findAll()
}