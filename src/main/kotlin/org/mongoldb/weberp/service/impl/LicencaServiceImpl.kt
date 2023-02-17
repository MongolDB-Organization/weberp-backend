package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.repository.LicencaRepository
import org.mongoldb.weberp.service.LicencaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LicencaServiceImpl(@Autowired private val repository: LicencaRepository) : LicencaService