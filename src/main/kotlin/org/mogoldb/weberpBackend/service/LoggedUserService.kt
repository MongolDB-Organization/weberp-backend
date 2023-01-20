package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class LoggedUserService {
    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        return usuarioRepository.findByEmail(email).getOrNull()
    }
}