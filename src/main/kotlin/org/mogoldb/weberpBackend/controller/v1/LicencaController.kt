package org.mogoldb.weberpBackend.controller.v1

import org.mogoldb.weberpBackend.delegate.DefaultController
import org.mogoldb.weberpBackend.entity.Licenca
import org.mogoldb.weberpBackend.service.LicencaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/licencas")
class LicencaController(@Autowired private val service: LicencaService) : DefaultController<Licenca, Long>(service)