package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadEstado

data class CadEstadoDto(
    var codigo: Long? = null,
    var descricao: String? = null,
    var uf: String? = null,
    var ibge: Long? = null,
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