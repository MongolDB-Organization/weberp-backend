package org.mongoldb.weberp.service

import org.mongoldb.weberp.dto.response.CadCidadeDetailedDto
import org.mongoldb.weberp.dto.response.CadCidadeDto
import org.mongoldb.weberp.dto.response.PageableDto

interface CadCidadeService {
    fun findAll(page: Int?, size: Int?, descricao: String?, uf: String?): PageableDto<CadCidadeDto>

    fun findById(id: Long) : CadCidadeDetailedDto
}