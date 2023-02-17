package org.mongoldb.weberp.service

import org.mongoldb.weberp.entity.Usuario

interface LoggedUserService {

    fun getLoggedUser(): Usuario?
}