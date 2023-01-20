package org.mogoldb.weberpBackend.dto.mapper

import org.mogoldb.weberpBackend.dto.response.ContratoDto
import org.mogoldb.weberpBackend.dto.request.UsuarioCreateUpdateDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto
import org.mogoldb.weberpBackend.dto.mapper.ContratoMapper.Companion.toDto
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Usuario

fun Usuario.toDto(): UsuarioDto {
    val dto = UsuarioDto()
    dto.codigo = codigo
    dto.nome = nome
    dto.email = email
    dto.telefone = telefone
    return dto
}

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

    detailedDto.contratos = contratos.map<Contrato, ContratoDto> { it: Contrato ->
        it.toDto()
    }

    return detailedDto
}

fun UsuarioCreateUpdateDto.toEntity(mergeEntity: Usuario?): Usuario {
    val entity = mergeEntity ?: Usuario()
    entity.nome = nome
    entity.email = email
    entity.telefone = telefone
    return entity
}
