package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.dto.response.EstadoUfDto
import org.mongoldb.weberp.dto.response.EstadoUfDto.Companion.toDto
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.EstadoUfRepository
import org.mongoldb.weberp.service.EstadoUfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class EstadoUfServiceImpl : EstadoUfService {

    @Autowired
    private lateinit var repository: EstadoUfRepository

    override fun findAll(): List<EstadoUfDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): EstadoUfDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDto()
    }
}