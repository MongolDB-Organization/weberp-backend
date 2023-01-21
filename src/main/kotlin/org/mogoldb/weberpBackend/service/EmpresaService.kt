package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.request.EmpresaCreateDto
import org.mogoldb.weberpBackend.dto.request.EmpresaUpdateDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDetailedDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDto

interface EmpresaService {
    fun findAll(): List<EmpresaDto>

    fun findById(id: Long): EmpresaDetailedDto

    fun create(dto: EmpresaCreateDto): EmpresaDetailedDto

    fun update(id: Long, dto: EmpresaUpdateDto): EmpresaDetailedDto
}