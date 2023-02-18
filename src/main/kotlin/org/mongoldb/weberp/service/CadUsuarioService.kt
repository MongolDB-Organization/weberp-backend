package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.CadUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.CadUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.CadUsuarioDto

interface CadUsuarioService {

    fun findAll(): List<CadUsuarioDto>

    fun findById(id: Long): CadUsuarioDetailedDto

    fun update(id: Long, dto: CadUsuarioUpdateDto): CadUsuarioDetailedDto
}
