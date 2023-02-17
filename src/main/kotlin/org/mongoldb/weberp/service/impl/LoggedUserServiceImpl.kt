package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.entity.Usuario
import org.mongoldb.weberp.repository.UsuarioRepository
import org.mongoldb.weberp.service.LoggedUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class LoggedUserServiceImpl : LoggedUserService {
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    override fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        return usuarioRepository.findByEmail(email).getOrNull()
    }
}