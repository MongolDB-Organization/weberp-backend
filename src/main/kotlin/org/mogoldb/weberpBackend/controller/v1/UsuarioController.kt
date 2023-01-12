package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.endpoint.NSDeleteEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSIndexEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSShowEndpoint
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Empresa
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

    private class DefaultUsuarioResponse(
        val nome: String,
        val email: String,
        val telefone: String,
        val adiministrador: Boolean,
        val contratos: Set<Contrato>,
        val empresas: Set<Empresa>,
    ) {
        companion object {
            fun fromUsuario(usuario: Usuario): DefaultUsuarioResponse {
                return DefaultUsuarioResponse(
                    usuario.nome!!,
                    usuario.email!!,
                    usuario.telefone!!,
                    usuario.administrador,
                    usuario.contratos,
                    usuario.empresas,
                )
            }
        }
    }

    @Autowired
    override lateinit var service: UsuarioService

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: Usuario, @PathVariable(name = "id") id: Long): ResponseEntity<DefaultUsuarioResponse> {
        return ResponseEntity.ok().body(DefaultUsuarioResponse.fromUsuario(service.update(body, id)))
    }
}