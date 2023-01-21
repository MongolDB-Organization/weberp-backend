package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.dto.request.ContratoCreateDto
import org.mogoldb.weberpBackend.dto.request.ContratoUpdateDto
import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto
import org.mogoldb.weberpBackend.service.impl.ContratoServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/contratos")
class ContratoController {

    @Autowired
    lateinit var service: ContratoServiceImpl

    @GetMapping
    fun index(): List<ContratoDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    fun show(@PathVariable(name = "id") id: Long): ContratoDetailedDto {
        return service.findById(id)
    }

    @PostMapping
    private fun store(@Valid @RequestBody body: ContratoCreateDto): ResponseEntity<ContratoDetailedDto> {
        return ResponseEntity.ok(service.create(body))
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: ContratoUpdateDto, @PathVariable(name = "id") id: Long): ResponseEntity<ContratoDetailedDto> {
        return ResponseEntity.ok(service.update(id, body))
    }
}
