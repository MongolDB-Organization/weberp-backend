package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.ContratoDto.Companion.toDto
import org.mongoldb.weberp.entity.Contrato
import org.mongoldb.weberp.entity.Empresa
import org.mongoldb.weberp.entity.Usuario
import java.time.LocalDateTime

data class UsuarioDetailedDto(
    var administrador: Boolean = false,
    var verificado: Boolean = false,
    var contratos: List<ContratoDto> = listOf<ContratoDto>(),
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var usuarioAtualizacao: UsuarioDto? = null,
    var usuarioCriacao: UsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null,
) : UsuarioDto() {
    companion object {
        fun Usuario.toDetailedDto(): UsuarioDetailedDto {
            val detailedDto = UsuarioDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.email = email
            detailedDto.telefone = telefone
            detailedDto.administrador = administrador
            detailedDto.dataModificacao = dataModificacao
            detailedDto.dataCriacao = dataCriacao
            detailedDto.empresas = empresas
            detailedDto.usuarioAtualizacao = usuarioAtualizacao?.toDto()
            detailedDto.usuarioCriacao = usuarioCriacao?.toDto()
            detailedDto.contratos = contratos.map { it: Contrato -> it.toDto() }
            return detailedDto
        }
    }
}
