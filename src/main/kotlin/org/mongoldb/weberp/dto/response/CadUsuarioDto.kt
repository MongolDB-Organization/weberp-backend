package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadUsuario

open class CadUsuarioDto(
    var codigo: Long = 0,
    var nome: String? = null,
    var email: String? = null,
    var telefone: String? = null,
) {

    companion object {
        fun CadUsuario.toDto(): CadUsuarioDto {
            val dto = CadUsuarioDto()
            dto.codigo = codigo
            dto.nome = nome
            dto.email = email
            dto.telefone = telefone
            return dto
        }
    }
}