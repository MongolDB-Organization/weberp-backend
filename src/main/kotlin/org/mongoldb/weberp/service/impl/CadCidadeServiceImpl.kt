package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.NSConstants
import org.mongoldb.weberp.dto.response.CadCidadeDetailedDto
import org.mongoldb.weberp.dto.response.CadCidadeDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.CadCidadeDto
import org.mongoldb.weberp.dto.response.CadCidadeDto.Companion.toDto
import org.mongoldb.weberp.dto.response.PageableDto
import org.mongoldb.weberp.entity.CadCidade
import org.mongoldb.weberp.entity.CadEstado
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.CadCidadeRepository
import org.mongoldb.weberp.service.CadCidadeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class CadCidadeServiceImpl : CadCidadeService {

    @Autowired
    private lateinit var repository: CadCidadeRepository

    override fun findAll(page: Int?, size: Int?, descricao: String?, uf: String?): PageableDto<CadCidadeDto> {
        val pageable: Pageable = PageRequest.of(page ?: NSConstants.PAGINACAO_PAGINA_INICIAL, size ?: NSConstants.PAGINACAO_TAMANHO_PADRAO)
        val cadCidade = CadCidade()
        val matcher = ExampleMatcher
            .matchingAll()
            .withIgnoreCase()

        if (!descricao.isNullOrBlank()) {
            cadCidade.descricao = descricao
            matcher.withMatcher(CadCidade::descricao::name.get()) { match ->
                match.contains()
            }
        }

        if (!uf.isNullOrBlank()) {
            cadCidade.cadEstado = CadEstado()
            cadCidade.cadEstado!!.uf = uf
            matcher.withMatcher(CadCidade::cadEstado::name.get() + "." + CadEstado::uf::name.get()) { match ->
                match.contains()
            }
        }

        val pageResults = repository.findAll(Example.of(cadCidade, matcher), pageable)
        return PageableDto(pageResults.size, pageResults.number, pageResults.totalPages, pageResults.content.map { it -> it.toDto() })
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): CadCidadeDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }
}