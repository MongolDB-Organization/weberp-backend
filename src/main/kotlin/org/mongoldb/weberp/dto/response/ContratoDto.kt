package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.Contrato

open class ContratoDto(
    var codigo: Long = 0,
    var nome: String? = null,
) {
    companion object {
        fun Contrato.toDto(): ContratoDto {
            val dto = ContratoDto()
            dto.codigo = codigo
            dto.nome = nome
            return dto
        }
    }
}