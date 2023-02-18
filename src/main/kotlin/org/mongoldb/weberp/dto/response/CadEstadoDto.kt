package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadEstado

data class CadEstadoDto(
    var codigo: Long = 0,
    var descricao: String = "",
    var uf: String = "",
    var ibge: Long = 0,
) {
    companion object {
        fun CadEstado.toDto(): CadEstadoDto {
            val dto = CadEstadoDto()
            dto.codigo = codigo
            dto.descricao = descricao
            dto.uf = uf
            dto.ibge = ibge
            return dto
        }
    }
}