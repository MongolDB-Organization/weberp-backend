package org.mongoldb.weberp.delegate.repository

import org.mongoldb.weberp.delegate.entity.NSContratoLevelEntity
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface NSContratoLevelRepository<OBJ: NSContratoLevelEntity> : NSRepository<OBJ>