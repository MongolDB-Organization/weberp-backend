package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserDetailsServiceImpl : org.mogoldb.weberpBackend.service.UserDetailsService {

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        val user = usuarioRepository.findByEmail(email!!).getOrNull()
        return if (user != null && user.email == email) {
            User(email, user.senha!!, ArrayList())
        } else {
            throw UsernameNotFoundException("User not found with email: $email")
        }
    }
}