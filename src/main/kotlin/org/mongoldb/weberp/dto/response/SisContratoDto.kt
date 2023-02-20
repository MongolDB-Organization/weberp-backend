package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.SisContrato

open class SisContratoDto(
    var codigo: Long = 0,
    var nome: String? = null,
) {
    companion object {
        fun SisContrato.toDto(): SisContratoDto {
            val dto = SisContratoDto()
            dto.codigo = codigo
            dto.nome = nome
            return dto
        }
    }
}