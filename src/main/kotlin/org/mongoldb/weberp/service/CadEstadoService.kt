package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.response.CadEstadoDto

interface CadEstadoService {
    fun findAll() : List<CadEstadoDto>

    fun findById(id: Long) : CadEstadoDto
}