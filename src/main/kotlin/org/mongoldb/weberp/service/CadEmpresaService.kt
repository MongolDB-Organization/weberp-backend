package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.CadEmpresaCreateDto
import org.mongoldb.weberp.dto.request.CadEmpresaUpdateDto
import org.mongoldb.weberp.dto.response.CadEmpresaDetailedDto
import org.mongoldb.weberp.dto.response.CadEmpresaDto

interface CadEmpresaService {
    fun findAll(): List<CadEmpresaDto>

    fun findById(id: Long): CadEmpresaDetailedDto

    fun create(dto: CadEmpresaCreateDto): CadEmpresaDetailedDto

    fun update(id: Long, dto: CadEmpresaUpdateDto): CadEmpresaDetailedDto
}