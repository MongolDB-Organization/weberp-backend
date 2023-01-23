package org.mogoldb.weberpBackend.repository;

import org.mogoldb.weberpBackend.entity.CidadeUf
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CidadeUfRepository : JpaRepository<CidadeUf, Long> {

    fun findAllByDescricaoContainingIgnoreCase(descricao: String, pageable: Pageable) : Page<CidadeUf>
}