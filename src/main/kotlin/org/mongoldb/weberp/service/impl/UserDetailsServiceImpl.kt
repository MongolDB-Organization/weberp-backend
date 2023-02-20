package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private lateinit var sisUsuarioRepository: SisUsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        val user = sisUsuarioRepository.findByEmail(email!!).getOrNull()
        return if (user != null && user.email == email) {
            User(email, user.senha!!, ArrayList())
        } else {
            throw UsernameNotFoundException("User not found with email: $email")
        }
    }
}
