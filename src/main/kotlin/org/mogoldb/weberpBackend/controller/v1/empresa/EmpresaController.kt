package org.mogoldb.weberpBackend.controller.v1.empresa

import org.mogoldb.weberpBackend.delegate.CrudController
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.service.EmpresaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/empresas")
class EmpresaController(@Autowired override val service: EmpresaService) : CrudController<Empresa, Long>()