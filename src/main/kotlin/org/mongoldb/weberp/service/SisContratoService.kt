package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.SisContratoCreateDto
import org.mongoldb.weberp.dto.request.SisContratoUpdateDto
import org.mongoldb.weberp.dto.response.SisContratoDetailedDto
import org.mongoldb.weberp.dto.response.SisContratoDto

interface SisContratoService {

    fun findAll(): List<SisContratoDto>

    fun findById(id: Long): SisContratoDetailedDto

    fun create(dto: SisContratoCreateDto): SisContratoDetailedDto

    fun update(id: Long, dto: SisContratoUpdateDto): SisContratoDetailedDto
}