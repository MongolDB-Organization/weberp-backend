package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.entity.CadUsuario
import org.mongoldb.weberp.repository.CadUsuarioRepository
import org.mongoldb.weberp.service.LoggedUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class LoggedUserServiceImpl : LoggedUserService {
    @Autowired
    private lateinit var cadUsuarioRepository: CadUsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    override fun getLoggedUser(): CadUsuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        return cadUsuarioRepository.findByEmail(email).getOrNull()
    }
}