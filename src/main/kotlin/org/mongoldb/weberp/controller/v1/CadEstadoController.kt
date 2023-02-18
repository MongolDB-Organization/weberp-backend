package org.mongoldb.weberp.controller.v1

import org.mongoldb.weberp.dto.response.CadEstadoDto
import org.mongoldb.weberp.service.impl.CadEstadoServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/cad-estados")
class CadEstadoController {

    @Autowired
    private lateinit var service: CadEstadoServiceImpl

    @GetMapping
    fun index(): ResponseEntity<List<CadEstadoDto>> {
        return ResponseEntity.ok(service.findAll())
    }

    @GetMapping("{id}")
    fun show(@PathVariable(name = "id") id: Long): ResponseEntity<CadEstadoDto> {
        return ResponseEntity.ok(service.findById(id))
    }
}