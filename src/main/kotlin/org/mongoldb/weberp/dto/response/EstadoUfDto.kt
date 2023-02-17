package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.EstadoUf

data class EstadoUfDto(
    var codigo: Long = 0,
    var descricao: String = "",
    var sigla: String = "",
    var codigoIbge: Long = 0,
) {
    companion object {
        fun EstadoUf.toDto(): EstadoUfDto {
            val dto = EstadoUfDto()
            dto.codigo = codigo
            dto.descricao = descricao
            dto.sigla = sigla
            dto.codigoIbge = codigoIbge
            return dto
        }
    }
}