package org.mongoldb.weberp.delegate.service

import org.mongoldb.weberp.delegate.entity.NSContratoLevelEntity
import org.mongoldb.weberp.delegate.repository.NSContratoLevelRepository
import org.springframework.beans.factory.annotation.Autowired

open class NSContratoLevelService<OB : NSContratoLevelEntity>(@Autowired private val repository: NSContratoLevelRepository<OB>) : NSService<OB>(repository)