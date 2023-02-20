package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.entity.SisUsuario
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.LoggedUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class LoggedUserServiceImpl : LoggedUserService {
    @Autowired
    private lateinit var sisUsuarioRepository: SisUsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    override fun getLoggedUser(): SisUsuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        return sisUsuarioRepository.findByEmail(email).getOrNull()
    }
}