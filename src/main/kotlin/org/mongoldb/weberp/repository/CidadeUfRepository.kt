package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CidadeUf
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CidadeUfRepository : JpaRepository<CidadeUf, Long> {

    fun findAllByEstadoUfSigla(sigla: String, pageable: Pageable): Page<CidadeUf>

    fun findAllByDescricaoContainingIgnoreCase(descricao: String, pageable: Pageable): Page<CidadeUf>

    fun findAllByDescricaoContainingIgnoreCaseAndEstadoUfSigla(descricao: String, sigla: String, pageable: Pageable): Page<CidadeUf>
}