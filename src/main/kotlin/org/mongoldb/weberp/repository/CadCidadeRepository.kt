package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CadCidade
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CadCidadeRepository : JpaRepository<CadCidade, Long> {

    fun findAllByCadEstadoUf(uf: String, pageable: Pageable): Page<CadCidade>

    fun findAllByDescricaoContainingIgnoreCase(descricao: String, pageable: Pageable): Page<CadCidade>

    fun findAllByDescricaoContainingIgnoreCaseAndCadEstadoUf(descricao: String, uf: String, pageable: Pageable): Page<CadCidade>
}