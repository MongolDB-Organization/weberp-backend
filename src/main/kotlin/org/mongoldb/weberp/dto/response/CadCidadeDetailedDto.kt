package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadEstadoDto.Companion.toDto
import org.mongoldb.weberp.entity.CadCidade

class CadCidadeDetailedDto(
    var cadEstadoUf: CadEstadoDto? = null,
) : CadCidadeDto() {

    companion object {
        fun CadCidade.toDetailedDto(): CadCidadeDetailedDto {
            val dto = CadCidadeDetailedDto()
            dto.codigo = codigo
            dto.ibge = ibge
            dto.descricao = descricao
            dto.uf = cadEstado?.uf
            dto.cadEstadoUf = cadEstado?.toDto()
            return dto
        }
    }
}