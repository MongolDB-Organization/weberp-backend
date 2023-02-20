package org.mongoldb.weberp.repository

import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.SisContrato
import org.springframework.stereotype.Repository


@Repository
interface SisContratoRepository : NSRepository<SisContrato> {

    fun findByNome(nome: String) : SisContrato?
}