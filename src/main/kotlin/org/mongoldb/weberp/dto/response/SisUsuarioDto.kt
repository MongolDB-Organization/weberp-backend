package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.SisUsuario

open class SisUsuarioDto(
    var codigo: Long = 0,
    var nome: String? = null,
    var email: String? = null,
    var telefone: String? = null,
) {

    companion object {
        fun SisUsuario.toDto(): SisUsuarioDto {
            val dto = SisUsuarioDto()
            dto.codigo = codigo
            dto.nome = nome
            dto.email = email
            dto.telefone = telefone
            return dto
        }
    }
}