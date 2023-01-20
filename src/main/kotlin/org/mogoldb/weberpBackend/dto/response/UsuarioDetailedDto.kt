package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.entity.Empresa
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
) : UsuarioDto()
