package org.mongoldb.weberp.controller.v1

import jakarta.validation.Valid
import org.mongoldb.weberp.dto.request.CadEmpresaCreateDto
import org.mongoldb.weberp.dto.request.CadEmpresaUpdateDto
import org.mongoldb.weberp.dto.response.CadEmpresaDetailedDto
import org.mongoldb.weberp.dto.response.CadEmpresaDto
import org.mongoldb.weberp.service.impl.CadEmpresaServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/cad-empresas")
class CadEmpresaController {

    @Autowired
    lateinit var service: CadEmpresaServiceImpl

    @GetMapping
    fun index(): List<CadEmpresaDto> {
        return service.findAll()
    }

    @GetMapping("{id}")
    fun show(@PathVariable id: Long): CadEmpresaDetailedDto {
        return service.findById(id)
    }

    @PostMapping
    fun store(@Valid @RequestBody body: CadEmpresaCreateDto): ResponseEntity<CadEmpresaDetailedDto> {
        return ResponseEntity.ok(service.create(body))
    }

    @PutMapping("{id}")
    fun update(@PathVariable(name = "id") id: Long, @Valid @RequestBody body: CadEmpresaUpdateDto): ResponseEntity<CadEmpresaDetailedDto> {
        return ResponseEntity.ok(service.update(id, body))
    }
}