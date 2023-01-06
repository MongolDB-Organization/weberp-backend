package org.mogoldb.weberpBackend.controller.v1

import org.mogoldb.weberpBackend.delegate.DefaultController
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/usuarios")
class UsuarioController(@Autowired private val service: UsuarioService) : DefaultController<Usuario, Long>(service)