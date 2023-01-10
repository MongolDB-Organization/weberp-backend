package org.mogoldb.weberpBackend.delegate.endpoint

import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.service.NSService
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

interface NSShowEndpoint<OB : NSEntity> {
    val service: NSService<OB>

    @GetMapping("/{id}")
    open fun show(@PathVariable(name = "id") id: Long): ResponseEntity<OB> {
        val existsPredicate = service.findById(id) ?: throw NotFoundException()
        return ResponseEntity.ok(existsPredicate)
    }

}