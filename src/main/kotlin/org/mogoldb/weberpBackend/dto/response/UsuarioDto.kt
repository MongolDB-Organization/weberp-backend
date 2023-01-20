package org.mogoldb.weberpBackend.dto.response

open class UsuarioDto(
    var codigo: Long = 0,
    var nome: String? = null,
    var email: String? = null,
    var telefone: String? = null,
)