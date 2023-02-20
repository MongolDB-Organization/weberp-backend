package org.mongoldb.weberp.service

import org.mongoldb.weberp.entity.SisUsuario

interface LoggedUserService {

    fun getLoggedUser(): SisUsuario?
}