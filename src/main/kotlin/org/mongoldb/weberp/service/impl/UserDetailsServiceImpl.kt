package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.repository.CadUsuarioRepository
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
    private lateinit var cadUsuarioRepository: CadUsuarioRepository

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        val user = cadUsuarioRepository.findByEmail(email!!).getOrNull()
        return if (user != null && user.email == email) {
            User(email, user.senha!!, ArrayList())
        } else {
            throw UsernameNotFoundException("User not found with email: $email")
        }
    }
}
