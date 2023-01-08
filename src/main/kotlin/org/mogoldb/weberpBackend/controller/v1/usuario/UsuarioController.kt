package org.mogoldb.weberpBackend.controller.v1.usuario

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.controller.v1.usuario.payload.request.CriarUsuarioRequest
import org.mogoldb.weberpBackend.controller.v1.usuario.payload.response.DefaultUsuarioResponse
import org.mogoldb.weberpBackend.delegate.crud.DeleteControllerCrud
import org.mogoldb.weberpBackend.delegate.crud.IndexControllerCrud
import org.mogoldb.weberpBackend.delegate.crud.ShowControllerCrud
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.Throws

@RestController
@RequestMapping("v1/usuarios")
class UsuarioController(@Autowired override val service: UsuarioService) :
    IndexControllerCrud<Usuario, Long>,
    ShowControllerCrud<Usuario, Long>,
    DeleteControllerCrud<Usuario, Long> {

    @PostMapping
    fun store(@Valid @RequestBody body: CriarUsuarioRequest): ResponseEntity<DefaultUsuarioResponse> {
        val usuario = Usuario()
        usuario.nome = body.nome
        usuario.email = body.email
        usuario.senha = body.senha
        usuario.administrador = false
        usuario.telefone = body.telefone
        val defaultResponse = DefaultUsuarioResponse.fromUsuario( service.save(usuario, null))
        return ResponseEntity.ok().body(defaultResponse)
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    fun update(@Valid @RequestBody body: Usuario, @PathVariable(name = "id") id: Long): ResponseEntity<DefaultUsuarioResponse> {
        val usuario = service.findById(id)
            ?: throw NotFoundException()
        body.codigo = id
        body.senha = usuario.senha
        body.administrador = usuario.administrador
        val usuarioAtualizado = service.save(body, id)
        val usuarioDefaultResponse = DefaultUsuarioResponse.fromUsuario(usuarioAtualizado)
        return ResponseEntity.ok().body(usuarioDefaultResponse)
    }
}