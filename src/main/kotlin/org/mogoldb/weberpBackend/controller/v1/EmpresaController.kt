package org.mogoldb.weberpBackend.controller.v1

import org.mogoldb.weberpBackend.delegate.NSController
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.service.EmpresaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/empresas")
class EmpresaController : NSController<Empresa>() {

    @Autowired
    override lateinit var service: EmpresaService
}