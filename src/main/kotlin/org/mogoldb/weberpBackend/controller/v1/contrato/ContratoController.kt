package org.mogoldb.weberpBackend.controller.v1.contrato

import org.mogoldb.weberpBackend.delegate.endpoint.NSShowEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSStoreEndpoint
import org.mogoldb.weberpBackend.delegate.endpoint.NSUpdateEndpoint
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.service.ContratoService
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/contratos")
class ContratoController : NSShowEndpoint<Contrato>, NSStoreEndpoint<Contrato>, NSUpdateEndpoint<Contrato> {

    @Autowired
    override lateinit var service: ContratoService
}
