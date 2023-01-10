package org.mogoldb.weberpBackend.delegate.service

import org.mogoldb.weberpBackend.delegate.entity.NSContratoLevelEntity
import org.mogoldb.weberpBackend.delegate.repository.NSContratoLevelRepository
import org.springframework.beans.factory.annotation.Autowired

open class NSContratoLevelService<OB : NSContratoLevelEntity>(@Autowired private val repository: NSContratoLevelRepository<OB>) : NSService<OB>(repository)