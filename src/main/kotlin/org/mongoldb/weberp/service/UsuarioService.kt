package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.UsuarioUpdateDto
import org.mongoldb.weberp.dto.response.UsuarioDetailedDto
import org.mongoldb.weberp.dto.response.UsuarioDto

interface UsuarioService {

    fun findAll(): List<UsuarioDto>

    fun findById(id: Long): UsuarioDetailedDto

    fun update(id: Long, dto: UsuarioUpdateDto): UsuarioDetailedDto
}
