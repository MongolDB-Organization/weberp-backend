package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.Usuario

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