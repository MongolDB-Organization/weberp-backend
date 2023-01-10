package org.mogoldb.weberpBackend.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsService(@Autowired val usuarioService: UsuarioService) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String?): UserDetails {
        val user = usuarioService.findByEmail(email!!)
        return if (user != null && user.email == email!!) {
            User(email, user.senha!!, ArrayList())
        } else {
            throw UsernameNotFoundException("User not found with email: $email")
        }
    }
}