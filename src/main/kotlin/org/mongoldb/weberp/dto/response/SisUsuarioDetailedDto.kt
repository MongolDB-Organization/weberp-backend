package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.ContratoDto.Companion.toDto
import org.mongoldb.weberp.entity.Contrato
import org.mongoldb.weberp.entity.Empresa
import org.mongoldb.weberp.entity.SisUsuario
import java.time.LocalDateTime

data class SisUsuarioDetailedDto(
    var administrador: Boolean = false,
    var verificado: Boolean = false,
    var contratos: List<ContratoDto> = listOf<ContratoDto>(),
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var sisUsuarioAtualizacao: SisUsuarioDto? = null,
    var sisUsuarioCriacao: SisUsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null,
) : SisUsuarioDto() {
    companion object {
        fun SisUsuario.toDetailedDto(): SisUsuarioDetailedDto {
            val detailedDto = SisUsuarioDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.email = email
            detailedDto.telefone = telefone
            detailedDto.administrador = administrador
            detailedDto.verificado = verificado
            detailedDto.dataModificacao = dataModificacao
            detailedDto.dataCriacao = dataCriacao
            detailedDto.empresas = empresas
            detailedDto.sisUsuarioAtualizacao = sisUsuarioAtualizacao?.toDto()
            detailedDto.sisUsuarioCriacao = sisUsuarioCriacao?.toDto()
            detailedDto.contratos = contratos.map { it: Contrato -> it.toDto() }
            return detailedDto
        }
    }
}
