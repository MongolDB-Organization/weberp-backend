package org.mogoldb.weberpBackend.repository

import jakarta.transaction.Transactional
import org.mogoldb.weberpBackend.delegate.repository.NSRepository
import org.mogoldb.weberpBackend.entity.Contrato
import org.springframework.data.jpa.repository.EntityGraph


@Transactional
interface ContratoRepository : NSRepository<Contrato> {

    @EntityGraph(attributePaths = ["empresas", "usuarios", "usuarioCriacao", "usuarioAtualizacao"])
    override fun findAll(): MutableList<Contrato>
}