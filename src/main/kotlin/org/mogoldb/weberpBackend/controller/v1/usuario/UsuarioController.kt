package org.mogoldb.weberpBackend.controller.v1.usuario

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.controller.v1.usuario.payload.response.DefaultUsuarioResponse
import org.mogoldb.weberpBackend.delegate.endpoint.NSDeleteEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSIndexEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSShowEndpoint
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("v1/usuarios")
class UsuarioController : NSIndexEndpoint<Usuario>, NSShowEndpoint<Usuario>, NSDeleteEndpoint<Usuario> {

    @Autowired
    override lateinit var service: UsuarioService

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    fun update(@Valid @RequestBody body: Usuario, @PathVariable(name = "id") id: Long): ResponseEntity<DefaultUsuarioResponse> {
        val usuario = service.findById(id) ?: throw NotFoundException()
        body.codigo = id
        body.senha = usuario.senha
        body.administrador = usuario.administrador
        val usuarioAtualizado = service.update(body, id)
        val usuarioDefaultResponse = DefaultUsuarioResponse.fromUsuario(usuarioAtualizado)
        return ResponseEntity.ok().body(usuarioDefaultResponse)
    }
}