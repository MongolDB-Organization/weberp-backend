package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.entity.Usuario

open class UsuarioDto(
    var codigo: Long = 0,
    var nome: String? = null,
    var email: String? = null,
    var telefone: String? = null,
) {

    companion object {
        fun Usuario.toDto(): UsuarioDto {
            val dto = UsuarioDto()
            dto.codigo = codigo
            dto.nome = nome
            dto.email = email
            dto.telefone = telefone
            return dto
        }
    }
}