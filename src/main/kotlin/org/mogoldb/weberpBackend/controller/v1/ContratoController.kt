package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mogoldb.weberpBackend.delegate.endpoint.NSShowEndpoint
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.ContratoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/contratos")
class ContratoController : NSShowEndpoint<Contrato> {

    @Autowired
    override lateinit var service: ContratoService

    private class ContratoStoreRequest(@get: NotNull @get: NotBlank val nome: String? = null)

    @PostMapping
    private fun store(@Valid @RequestBody body: ContratoStoreRequest): ResponseEntity<Contrato> {
        val contrato = Contrato(nome = body.nome)
        return ResponseEntity.ok(service.create(contrato))
    }

    @PutMapping("/{id}")
    @Throws(NotFoundException::class)
    private fun update(@Valid @RequestBody body: ContratoStoreRequest, @PathVariable(name = "id") id: Long): ResponseEntity<Contrato> {
        val contrato = service.findById(id) ?: throw NotFoundException()
        contrato.nome = body.nome
        return ResponseEntity.ok(service.update(contrato, id))
    }
}
