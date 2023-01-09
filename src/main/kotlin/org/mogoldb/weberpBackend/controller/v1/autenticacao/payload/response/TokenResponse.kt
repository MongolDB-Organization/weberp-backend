package org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.response

class TokenResponse(val token: String, val logged: Boolean = true)