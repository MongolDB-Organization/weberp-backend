package org.mogoldb.weberpBackend.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

interface UserDetailsService  : UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails
}