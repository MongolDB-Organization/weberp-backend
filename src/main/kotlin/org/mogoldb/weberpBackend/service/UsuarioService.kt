package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.request.UsuarioUpdateDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto

interface UsuarioService {

    fun findAll(): List<UsuarioDto>

    fun findById(id: Long): UsuarioDetailedDto

    fun update(id: Long, dto: UsuarioUpdateDto): UsuarioDetailedDto
}
