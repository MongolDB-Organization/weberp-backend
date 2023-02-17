package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.request.ContratoCreateDto
import org.mongoldb.weberp.dto.request.ContratoUpdateDto
import org.mongoldb.weberp.dto.response.ContratoDetailedDto
import org.mongoldb.weberp.dto.response.ContratoDto

interface ContratoService {

    fun findAll(): List<ContratoDto>

    fun findById(id: Long): ContratoDetailedDto

    fun create(dto: ContratoCreateDto): ContratoDetailedDto

    fun update(id: Long, dto: ContratoUpdateDto): ContratoDetailedDto
}