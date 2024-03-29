package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.dto.request.EmpresaCreateDto
import org.mogoldb.weberpBackend.dto.request.EmpresaUpdateDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDetailedDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDto
import org.mogoldb.weberpBackend.service.impl.EmpresaServiceImpl
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
@RequestMapping("v1/empresas")
class EmpresaController {

    @Autowired
    lateinit var service: EmpresaServiceImpl

    @GetMapping
    fun index(): List<EmpresaDto> {
        return service.findAll()
    }

    @GetMapping("{id}")
    fun show(@PathVariable id: Long): EmpresaDetailedDto {
        return service.findById(id)
    }

    @PostMapping
    fun store(@Valid @RequestBody body: EmpresaCreateDto): ResponseEntity<EmpresaDetailedDto> {
        return ResponseEntity.ok(service.create(body))
    }

    @PutMapping("{id}")
    fun update(@PathVariable(name = "id") id: Long, @Valid @RequestBody body: EmpresaUpdateDto): ResponseEntity<EmpresaDetailedDto> {
        return ResponseEntity.ok(service.update(id, body))
    }
}