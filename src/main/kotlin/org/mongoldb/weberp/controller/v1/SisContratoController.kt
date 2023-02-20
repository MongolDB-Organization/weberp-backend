package org.mongoldb.weberp.controller.v1

import jakarta.validation.Valid
import org.mongoldb.weberp.dto.request.SisContratoCreateDto
import org.mongoldb.weberp.dto.request.SisContratoUpdateDto
import org.mongoldb.weberp.dto.response.SisContratoDetailedDto
import org.mongoldb.weberp.dto.response.SisContratoDto
import org.mongoldb.weberp.service.impl.SisContratoServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/sis-contratos")
class SisContratoController {

    @Autowired
    lateinit var service: SisContratoServiceImpl

    @GetMapping
    fun index(): List<SisContratoDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun show(@PathVariable(name = "id") id: Long): SisContratoDetailedDto {
        return service.findById(id)
    }

    @PostMapping
    private fun store(@Valid @RequestBody body: SisContratoCreateDto): ResponseEntity<SisContratoDetailedDto> {
        return ResponseEntity.ok(service.create(body))
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: SisContratoUpdateDto, @PathVariable(name = "id") id: Long): ResponseEntity<SisContratoDetailedDto> {
        return ResponseEntity.ok(service.update(id, body))
    }
}
