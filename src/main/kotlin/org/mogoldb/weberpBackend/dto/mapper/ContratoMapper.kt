package org.mogoldb.weberpBackend.dto.mapper

import org.mogoldb.weberpBackend.dto.request.ContratoCreateUpdateDto
import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Usuario

class ContratoMapper {

    companion object {

        fun Contrato.toDto(): ContratoDto {
            val dto = ContratoDto()
            dto.codigo = codigo
            dto.nome = nome
            return dto
        }

        fun Contrato.toDetailedDto(): ContratoDetailedDto {
            val detailedDto = ContratoDetailedDto()
            detailedDto.codigo = codigo
            detailedDto.nome = nome
            detailedDto.empresas = empresas
            detailedDto.usuarioProprietario = usuarioProprietario?.toDto()
            detailedDto.usuarioCriacao = usuarioCriacao?.toDto()
            detailedDto.usuarioAtualizacao = usuarioAtualizacao?.toDto()
            detailedDto.usuarios = usuarios.map<Usuario, UsuarioDto> { it -> it.toDto() }.toMutableList()
            detailedDto.dataCriacao = dataCriacao
            detailedDto.dataModificacao = dataModificacao
            return detailedDto
        }

        open fun ContratoCreateUpdateDto.toEntity(mergeEntity: Contrato?): Contrato {
            val entity = mergeEntity ?: Contrato()
            entity.nome = nome ?: ""
            return entity
        }
    }
}