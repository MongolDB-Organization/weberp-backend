package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.dto.response.CadEstadoDto
import org.mongoldb.weberp.dto.response.CadEstadoDto.Companion.toDto
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.EstadoUfRepository
import org.mongoldb.weberp.service.CadEstadoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class CadEstadoServiceImpl : CadEstadoService {

    @Autowired
    private lateinit var repository: EstadoUfRepository

    override fun findAll(): List<CadEstadoDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): CadEstadoDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDto()
    }
}