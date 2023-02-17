package org.mongoldb.weberp.controller.v1

import org.mongoldb.weberp.dto.response.EstadoUfDto
import org.mongoldb.weberp.service.impl.EstadoUfServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/estados-uf")
class EstadoUfController {

    @Autowired
    private lateinit var service: EstadoUfServiceImpl

    @GetMapping
    fun index(): ResponseEntity<List<EstadoUfDto>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping("{id}")
    fun show(@PathVariable(name = "id") id: Long): ResponseEntity<EstadoUfDto> {
        return ResponseEntity.ok(service.findById(id))
    }
}