package org.mongoldb.weberp.service

import org.mongoldb.weberp.entity.CadUsuario

interface LoggedUserService {

    fun getLoggedUser(): CadUsuario?
}