package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.response.EstadoUfDto

interface EstadoUfService {
    fun findAll() : List<EstadoUfDto>

    fun findById(id: Long) : EstadoUfDto
}