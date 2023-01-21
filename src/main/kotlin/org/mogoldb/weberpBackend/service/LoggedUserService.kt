package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.entity.Usuario

interface LoggedUserService {

    fun getLoggedUser(): Usuario?
}