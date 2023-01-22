package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.entity.Usuario

interface AutenticacaoService {
    fun getLoggedUser(): Usuario?

    fun authenticateWithEmailAndPassword(email: String, password: String)

    fun signin(email: String, password: String): String

    fun signup(nome: String, email: String, password: String, telefone: String?): String

    fun sendVerificationCode(email: String)

    fun validateVerificationCode(verifyCode: String)
}