package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.UsuarioDto.Companion.toDto
import org.mongoldb.weberp.entity.Contrato
import org.mongoldb.weberp.entity.Empresa
import java.time.LocalDateTime

data class ContratoDetailedDto(
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var usuarios: MutableList<UsuarioDto> = arrayListOf<UsuarioDto>(),
    var usuarioProprietario: UsuarioDto? = null,
    var usuarioCriacao: UsuarioDto? = null,
    var usuarioAtualizacao: UsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null
) : ContratoDto() {

    companion object {
        fun Contrato.toDetailedDto(): ContratoDetailedDto {
            val detailedDto = ContratoDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.empresas = empresas
            detailedDto.usuarioProprietario = usuarioProprietario?.toDto()
            detailedDto.usuarioCriacao = usuarioCriacao?.toDto()
            detailedDto.usuarioAtualizacao = usuarioAtualizacao?.toDto()
            detailedDto.usuarios = usuarios.map { it -> it.toDto() }.toMutableList()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            return detailedDto
        }
    }
}
