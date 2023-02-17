package org.mongoldb.weberp.repository

import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.Contrato
import org.springframework.stereotype.Repository


@Repository
interface ContratoRepository : NSRepository<Contrato> {

}