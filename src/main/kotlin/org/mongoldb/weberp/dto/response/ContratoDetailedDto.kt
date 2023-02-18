package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadUsuarioDto.Companion.toDto
import org.mongoldb.weberp.entity.Contrato
import org.mongoldb.weberp.entity.Empresa
import java.time.LocalDateTime

data class ContratoDetailedDto(
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var usuarios: MutableList<CadUsuarioDto> = arrayListOf<CadUsuarioDto>(),
    var usuarioProprietario: CadUsuarioDto? = null,
    var usuarioCriacao: CadUsuarioDto? = null,
    var usuarioAtualizacao: CadUsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null
) : ContratoDto() {

    companion object {
        fun Contrato.toDetailedDto(): ContratoDetailedDto {
            val detailedDto = ContratoDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.empresas = empresas
            detailedDto.usuarioProprietario = cadUsuarioProprietario?.toDto()
            detailedDto.usuarioCriacao = cadUsuarioCriacao?.toDto()
            detailedDto.usuarioAtualizacao = cadUsuarioAtualizacao?.toDto()
            detailedDto.usuarios = cadUsuarios.map { it -> it.toDto() }.toMutableList()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            return detailedDto
        }
    }
}
