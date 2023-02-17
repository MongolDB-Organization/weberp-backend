package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.ContratoDto.Companion.toDto
import org.mongoldb.weberp.dto.response.UsuarioDto.Companion.toDto
import org.mongoldb.weberp.entity.Empresa
import java.time.LocalDateTime

data class EmpresaDetailedDto(
    var contrato: ContratoDto? = null,
    var usuarioAtualizacao: UsuarioDto? = null,
    var usuarioCriacao: UsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null,
) : EmpresaDto() {

    companion object {
        fun Empresa.toDetailedDto(): EmpresaDetailedDto {
            val detailedDto = EmpresaDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.razaoSocial = razaoSocial
            detailedDto.nomeFantasia = nomeFantasia
            detailedDto.incricaoEstadual = incricaoEstadual
            detailedDto.cnpj = cnpj
            detailedDto.email = email
            detailedDto.telefone = telefone
            detailedDto.contrato = contrato?.toDto()
            detailedDto.usuarioAtualizacao = usuarioAtualizacao?.toDto()
            detailedDto.usuarioCriacao = usuarioCriacao?.toDto()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            return detailedDto
        }
    }
}