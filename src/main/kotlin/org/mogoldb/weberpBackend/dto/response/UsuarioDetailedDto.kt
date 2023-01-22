package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.dto.response.ContratoDto.Companion.toDto
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.entity.Usuario
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
