package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.dto.response.EstadoUfDto
import org.mogoldb.weberpBackend.dto.response.EstadoUfDto.Companion.toDto
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.EstadoUfRepository
import org.mogoldb.weberpBackend.service.EstadoUfService
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