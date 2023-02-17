package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.response.EstadoUfDto

interface EstadoUfService {
    fun findAll() : List<EstadoUfDto>

    fun findById(id: Long) : EstadoUfDto
}