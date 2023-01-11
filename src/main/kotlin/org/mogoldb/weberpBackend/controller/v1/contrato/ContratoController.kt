package org.mogoldb.weberpBackend.controller.v1.contrato

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.endpoint.NSShowEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSStoreEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSUpdateEndpoint
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.ContratoService
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/contratos")
class ContratoController : NSShowEndpoint<Contrato>, NSStoreEndpoint<Contrato>, NSUpdateEndpoint<Contrato> {

    @Autowired
    override lateinit var service: ContratoService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @GetMapping
    fun index(): List<Contrato> {
        val loggedUser = service.getLoggedUser()
        return service.findAll()
    }

    @GetMapping("/{id}")
    override fun show(@PathVariable(name = "id") id: Long): ResponseEntity<Contrato> {
        val loggedUser = service.getLoggedUser()
        val contratosCodigos = usuarioService.getContratosCodigos(loggedUser!!)
        if (contratosCodigos.contains(loggedUser.codigo)) {
            throw NotFoundException()
        }
        return super.show(id)
    }

    @PostMapping
    override fun store(@Valid @RequestBody body: Contrato): ResponseEntity<Contrato> {
        val loggedUser = service.getLoggedUser()
        body.usuarioProprietario = loggedUser
        body.usuarios = hashSetOf(loggedUser!!)
        return super.store(body)
    }
}
