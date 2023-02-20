package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadEmpresaDto.Companion.toDto
import org.mongoldb.weberp.dto.response.SisContratoDto.Companion.toDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto.Companion.toDto
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.entity.CadEmpresa
import java.time.LocalDateTime

data class SisContratoDetailedDto(
    var cadEmpresas: MutableList<CadEmpresaDto> = arrayListOf<CadEmpresaDto>(),
    var sisUsuarios: MutableList<SisUsuarioDto> = arrayListOf<SisUsuarioDto>(),
    var sisUsuarioProprietario: SisUsuarioDto? = null,
    var sisUsuarioCriacao: SisUsuarioDto? = null,
    var sisUsuarioAtualizacao: SisUsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null
) : SisContratoDto() {

    companion object {
        fun SisContrato.toDetailedDto(): SisContratoDetailedDto {
            val detailedDto = SisContratoDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.cadEmpresas = cadEmpresas.map { it -> it.toDto() }.toMutableList()
            detailedDto.sisUsuarioProprietario = sisUsuarioProprietario?.toDto()
            detailedDto.sisUsuarioCriacao = sisUsuarioCriacao?.toDto()
            detailedDto.sisUsuarioAtualizacao = sisUsuarioAtualizacao?.toDto()
            detailedDto.sisUsuarios = sisUsuarios.map { it -> it.toDto() }.toMutableList()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            return detailedDto
        }
    }
}
