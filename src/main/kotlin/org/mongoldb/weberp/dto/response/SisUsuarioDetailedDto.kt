package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.SisContratoDto.Companion.toDto
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.entity.SisUsuario
import java.time.LocalDateTime

data class SisUsuarioDetailedDto(
    var administrador: Boolean = false,
    var verificado: Boolean = false,
    var sisContratos: List<SisContratoDto> = listOf<SisContratoDto>(),
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
            detailedDto.sisUsuarioAtualizacao = sisUsuarioAtualizacao?.toDto()
            detailedDto.sisUsuarioCriacao = sisUsuarioCriacao?.toDto()
            detailedDto.sisContratos = sisContratos.map { it: SisContrato -> it.toDto() }
            return detailedDto
        }
    }
}
