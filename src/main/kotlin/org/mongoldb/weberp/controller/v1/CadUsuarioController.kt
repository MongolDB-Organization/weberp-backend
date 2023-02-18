package org.mongoldb.weberp.controller.v1

import jakarta.validation.Valid
import org.mongoldb.weberp.dto.request.CadUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.CadUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.CadUsuarioDto
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.service.impl.CadUsuarioServiceImpl
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
@RequestMapping("v1/cad-usuarios")
class CadUsuarioController {

    @Autowired
    lateinit var service: CadUsuarioServiceImpl

    @GetMapping
    fun index(): List<CadUsuarioDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    private fun show(@PathVariable(name = "id") id: Long): CadUsuarioDetailedDto {
        return service.findById(id)
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: CadUsuarioUpdateDto, @PathVariable(name = "id") id: Long): ResponseEntity<CadUsuarioDetailedDto> {
        return ResponseEntity.ok().body(service.update(id, body))
    }
}