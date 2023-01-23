package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.dto.response.CidadeUfDetailedDto
import org.mogoldb.weberpBackend.dto.response.CidadeUfDetailedDto.Companion.toDetailedDto
import org.mogoldb.weberpBackend.dto.response.CidadeUfDto
import org.mogoldb.weberpBackend.dto.response.CidadeUfDto.Companion.toDto
import org.mogoldb.weberpBackend.dto.response.PageableDto
import org.mogoldb.weberpBackend.entity.CidadeUf
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.CidadeUfRepository
import org.mogoldb.weberpBackend.service.CidadeUfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull


@Service
class CidadeUfServiceImpl : CidadeUfService {

    @Autowired
    private lateinit var repository: CidadeUfRepository

    override fun findAll(page: Int?, size: Int?, descricao: String?, sigla: String?): PageableDto<CidadeUfDto> {
        val pageable: Pageable = PageRequest.of(page ?: 0, size ?: 20)
        val pageResults: Page<CidadeUf> = if (!sigla.isNullOrBlank() && !descricao.isNullOrBlank()) {
            repository.findAllByDescricaoContainingIgnoreCaseAndEstadoUfSigla(descricao, sigla, pageable)
        } else if (!sigla.isNullOrBlank()) {
            repository.findAllByEstadoUfSigla(sigla, pageable)
        } else if (!descricao.isNullOrBlank()) {
            repository.findAllByDescricaoContainingIgnoreCase(descricao, pageable)
        } else {
            repository.findAll(pageable)
        }
        return PageableDto(pageResults.size, pageResults.number, pageResults.totalPages, pageResults.content.map { it -> it.toDto() })
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): CidadeUfDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }
}