package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.SisUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.SisUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto

interface SisUsuarioService {

    fun findAll(): List<SisUsuarioDto>

    fun findById(id: Long): SisUsuarioDetailedDto

    fun update(id: Long, dto: SisUsuarioUpdateDto): SisUsuarioDetailedDto
}
