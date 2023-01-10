package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.service.NSService
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.repository.ContratoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContratoService(@Autowired private val repository: ContratoRepository) : NSService<Contrato>(repository)