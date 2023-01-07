package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.DefaultService
import org.mogoldb.weberpBackend.entity.Licenca
import org.mogoldb.weberpBackend.repository.LicencaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LicencaService(@Autowired private val repository: LicencaRepository) : DefaultService<Licenca, Long>(repository)