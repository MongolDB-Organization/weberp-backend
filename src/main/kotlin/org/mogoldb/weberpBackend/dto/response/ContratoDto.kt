package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.entity.Contrato

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