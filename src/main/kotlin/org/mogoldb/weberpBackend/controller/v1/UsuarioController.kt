package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.dto.request.UsuarioCreateUpdateDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.UsuarioService
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
@RequestMapping("v1/usuarios")
class UsuarioController {

    @Autowired
    lateinit var service: UsuarioService

    @GetMapping
    fun index(): List<UsuarioDto> {
        return service.findAll()
    }

    @GetMapping("/{id}")
    private fun show(@PathVariable(name = "id") id: Long): UsuarioDetailedDto {
        return service.findById(id)
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: UsuarioCreateUpdateDto, @PathVariable(name = "id") id: Long): ResponseEntity<UsuarioDetailedDto> {
        return ResponseEntity.ok().body(service.update(body, id))
    }
}