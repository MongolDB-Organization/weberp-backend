package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadEmpresaEnderecoDto.Companion.toDto
import org.mongoldb.weberp.dto.response.SisContratoDto.Companion.toDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto.Companion.toDto
import org.mongoldb.weberp.entity.CadEmpresa
import java.time.LocalDateTime

data class CadEmpresaDetailedDto(
    var sisContrato: SisContratoDto? = null,
    var sisUsuarioAtualizacao: SisUsuarioDto? = null,
    var sisUsuarioCriacao: SisUsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null,
    var cadEmpresaEnderecos: MutableSet<CadEmpresaEnderecoDto> = hashSetOf<CadEmpresaEnderecoDto>()
) : CadEmpresaDto() {

    companion object {
        fun CadEmpresa.toDetailedDto(): CadEmpresaDetailedDto {
            val detailedDto = CadEmpresaDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.razaoSocial = razaoSocial
            detailedDto.nomeFantasia = nomeFantasia
            detailedDto.incricaoEstadual = incricaoEstadual
            detailedDto.cnpj = cnpj
            detailedDto.email = email
            detailedDto.telefone = telefone
            detailedDto.sisContrato = sisContrato?.toDto()
            detailedDto.sisUsuarioAtualizacao = sisUsuarioAtualizacao?.toDto()
            detailedDto.sisUsuarioCriacao = sisUsuarioCriacao?.toDto()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            detailedDto.cadEmpresaEnderecos = cadEmpresaEnderecos.map { it -> it.toDto() }.toHashSet()
            return detailedDto
        }
    }
}