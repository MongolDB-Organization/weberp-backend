package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.dto.response.CidadeUfDetailedDto
import org.mongoldb.weberp.dto.response.CidadeUfDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.CidadeUfDto
import org.mongoldb.weberp.dto.response.CidadeUfDto.Companion.toDto
import org.mongoldb.weberp.dto.response.PageableDto
import org.mongoldb.weberp.entity.CidadeUf
import org.mongoldb.weberp.entity.EstadoUf
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.CidadeUfRepository
import org.mongoldb.weberp.service.CidadeUfService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class CidadeUfServiceImpl : CidadeUfService {

    @Autowired
    private lateinit var repository: CidadeUfRepository

    override fun findAll(page: Int?, size: Int?, descricao: String?, sigla: String?): PageableDto<CidadeUfDto> {
        val pageable: Pageable = PageRequest.of(page ?: 0, size ?: 20)

        val cidadeUf = CidadeUf(descricao = descricao ?: "")
        val matcher = ExampleMatcher.matchingAny()
            .withIgnorePaths("codigo", "codigoIbge", "estadoUf")


        if (!descricao.isNullOrBlank()) {
            matcher
                .withMatcher(CidadeUf::descricao::name.get()) { match -> match.contains() }
                .withMatcher(CidadeUf::descricao::name.get()) { match -> match.ignoreCase() }
        }

        if (!sigla.isNullOrBlank()) {
            matcher
                .withMatcher(CidadeUf::estadoUf::name.get() + "." + EstadoUf::sigla::name::get) { match -> match.contains() }
                .withMatcher(CidadeUf::descricao::name.get()) { match -> match.ignoreCase() }
        } else {
            matcher.withIgnorePaths()
        }

//        val pageResults: Page<CidadeUf> = if (!sigla.isNullOrBlank() && !descricao.isNullOrBlank()) {
//            repository.findAllByDescricaoContainingIgnoreCaseAndEstadoUfSigla(descricao, sigla, pageable)
//        } else if (!sigla.isNullOrBlank()) {
//            repository.findAllByEstadoUfSigla(sigla, pageable)
//        } else if (!descricao.isNullOrBlank()) {
//            repository.findAllByDescricaoContainingIgnoreCase(descricao, pageable)
//        } else {
//            repository.findAll(pageable)
//        }
        val pageResults = repository.findAll(Example.of(cidadeUf, matcher), pageable)
        return PageableDto(pageResults.size, pageResults.number, pageResults.totalPages, pageResults.content.map { it -> it.toDto() })
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): CidadeUfDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }
}