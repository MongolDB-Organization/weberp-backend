package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.ContratoDto.Companion.toDto
import org.mongoldb.weberp.entity.Contrato
import org.mongoldb.weberp.entity.Empresa
import org.mongoldb.weberp.entity.CadUsuario
import java.time.LocalDateTime

data class CadUsuarioDetailedDto(
    var administrador: Boolean = false,
    var verificado: Boolean = false,
    var contratos: List<ContratoDto> = listOf<ContratoDto>(),
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var cadUsuarioAtualizacao: CadUsuarioDto? = null,
    var cadUsuarioCriacao: CadUsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null,
) : CadUsuarioDto() {
    companion object {
        fun CadUsuario.toDetailedDto(): CadUsuarioDetailedDto {
            val detailedDto = CadUsuarioDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.email = email
            detailedDto.telefone = telefone
            detailedDto.administrador = administrador
            detailedDto.verificado = verificado
            detailedDto.dataModificacao = dataModificacao
            detailedDto.dataCriacao = dataCriacao
            detailedDto.empresas = empresas
            detailedDto.cadUsuarioAtualizacao = cadUsuarioAtualizacao?.toDto()
            detailedDto.cadUsuarioCriacao = cadUsuarioCriacao?.toDto()
            detailedDto.contratos = contratos.map { it: Contrato -> it.toDto() }
            return detailedDto
        }
    }
}
