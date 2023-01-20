package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.entity.Empresa
import java.time.LocalDateTime

data class ContratoDetailedDto(
    var empresas: Set<Empresa> = HashSet<Empresa>(),
    var usuarios: MutableList<UsuarioDto> = arrayListOf<UsuarioDto>(),
    var usuarioProprietario: UsuarioDto? = null,
    var usuarioCriacao: UsuarioDto? = null,
    var usuarioAtualizacao: UsuarioDto? = null,
    var dataCriacao: LocalDateTime? = null,
    var dataModificacao: LocalDateTime? = null
) : ContratoDto()
