package org.mongoldb.weberp.controller.v1

import jakarta.validation.Valid
import org.mongoldb.weberp.dto.request.SisUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.SisUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.service.impl.SisUsuarioServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("v1/sis-usuarios")
class CadUsuarioController {

    @Autowired
    lateinit var service: SisUsuarioServiceImpl

    @GetMapping
    fun index(): List<SisUsuarioDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    private fun show(@PathVariable(name = "id") id: Long): SisUsuarioDetailedDto {
        return service.findById(id)
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: SisUsuarioUpdateDto, @PathVariable(name = "id") id: Long): ResponseEntity<SisUsuarioDetailedDto> {
        return ResponseEntity.ok().body(service.update(id, body))
    }
}