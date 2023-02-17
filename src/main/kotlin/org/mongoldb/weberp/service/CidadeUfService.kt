package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.response.CidadeUfDetailedDto
import org.mongoldb.weberp.dto.response.CidadeUfDto
import org.mongoldb.weberp.dto.response.PageableDto

interface CidadeUfService {
    fun findAll(page: Int?, size: Int?, descricao: String?, sigla: String?): PageableDto<CidadeUfDto>

    fun findById(id: Long) : CidadeUfDetailedDto
}