package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.dto.response.EstadoUfDto.Companion.toDto
import org.mogoldb.weberpBackend.entity.CidadeUf

class CidadeUfDetailedDto(
    var estadoUf: EstadoUfDto? = null,
) : CidadeUfDto() {

    companion object {
        fun CidadeUf.toDetailedDto(): CidadeUfDetailedDto {
            val dto = CidadeUfDetailedDto()
            dto.codigo = codigo
            dto.codigoIbge = codigoIbge
            dto.descricao = descricao
            dto.siglaEstado = estadoUf.sigla
            dto.estadoUf = estadoUf.toDto()
            return dto
        }
    }
}