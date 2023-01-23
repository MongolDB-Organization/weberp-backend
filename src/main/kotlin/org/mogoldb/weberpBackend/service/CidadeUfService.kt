package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.response.CidadeUfDetailedDto
import org.mogoldb.weberpBackend.dto.response.CidadeUfDto
import org.mogoldb.weberpBackend.dto.response.PageableDto

interface CidadeUfService {
    fun findAll(page: Int?, size: Int?, descricao: String?): PageableDto<CidadeUfDto>

    fun findById(id: Long) : CidadeUfDetailedDto
}