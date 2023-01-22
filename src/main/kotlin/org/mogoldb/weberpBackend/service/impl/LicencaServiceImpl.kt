package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.repository.LicencaRepository
import org.mogoldb.weberpBackend.service.LicencaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LicencaServiceImpl(@Autowired private val repository: LicencaRepository) : LicencaService