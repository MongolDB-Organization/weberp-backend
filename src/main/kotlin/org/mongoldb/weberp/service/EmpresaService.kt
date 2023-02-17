package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.EmpresaCreateDto
import org.mongoldb.weberp.dto.request.EmpresaUpdateDto
import org.mongoldb.weberp.dto.response.EmpresaDetailedDto
import org.mongoldb.weberp.dto.response.EmpresaDto

interface EmpresaService {
    fun findAll(): List<EmpresaDto>

    fun findById(id: Long): EmpresaDetailedDto

    fun create(dto: EmpresaCreateDto): EmpresaDetailedDto

    fun update(id: Long, dto: EmpresaUpdateDto): EmpresaDetailedDto
}