package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CidadeUf

open class CidadeUfDto(
    var codigo: Long = 0,
    var codigoIbge: Long = 0,
    var descricao: String = "",
    var siglaEstado: String = ""
) {
    companion object {
        fun CidadeUf.toDto(): CidadeUfDto {
            val dto = CidadeUfDto()
            dto.codigo = codigo
            dto.codigoIbge = codigoIbge
            dto.descricao = descricao
            dto.siglaEstado = estadoUf.sigla
            return dto
        }
    }
}