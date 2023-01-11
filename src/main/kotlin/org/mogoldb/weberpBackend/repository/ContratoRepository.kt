package org.mogoldb.weberpBackend.repository

import org.mogoldb.weberpBackend.delegate.repository.NSRepository
import org.mogoldb.weberpBackend.entity.Contrato
import org.springframework.data.jpa.repository.Query

interface ContratoRepository : NSRepository<Contrato> {

}
