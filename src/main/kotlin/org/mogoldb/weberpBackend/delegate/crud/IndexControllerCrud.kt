package org.mogoldb.weberpBackend.delegate.crud

import org.mogoldb.weberpBackend.delegate.DefaultEntity
import org.mogoldb.weberpBackend.delegate.DefaultService
import org.springframework.web.bind.annotation.GetMapping

interface IndexControllerCrud<OB : DefaultEntity, PK : Long> {
    val service: DefaultService<OB, PK>

    @GetMapping
    open fun index(): List<OB> = service.findAll()
}