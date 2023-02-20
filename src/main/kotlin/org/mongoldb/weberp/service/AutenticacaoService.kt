package org.mongoldb.weberp.service

import org.mongoldb.weberp.entity.SisUsuario

interface AutenticacaoService {
    fun getLoggedUser(): SisUsuario?

    fun authenticateWithEmailAndPassword(email: String, password: String)

    fun signin(email: String, password: String): String

    fun signup(nome: String, email: String, password: String, telefone: String?): String

    fun sendVerificationCode(email: String)

    fun validateVerificationCode(verifyCode: String)
}