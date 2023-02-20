package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.repository.SisLicencaRepository
import org.mongoldb.weberp.service.SisLicencaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SisLicencaServiceImpl(@Autowired private val repository: SisLicencaRepository) : SisLicencaService