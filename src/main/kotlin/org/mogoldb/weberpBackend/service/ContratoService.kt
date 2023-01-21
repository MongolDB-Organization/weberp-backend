package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.request.ContratoCreateDto
import org.mogoldb.weberpBackend.dto.request.ContratoUpdateDto
import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto

interface ContratoService {

    fun findAll(): List<ContratoDto>

    fun findById(id: Long): ContratoDetailedDto

    fun create(dto: ContratoCreateDto): ContratoDetailedDto

    fun update(id: Long, dto: ContratoUpdateDto): ContratoDetailedDto
}