package org.mogoldb.weberpBackend.delegate.repository

import org.mogoldb.weberpBackend.delegate.entity.NSContratoLevelEntity
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface NSContratoLevelRepository<OBJ: NSContratoLevelEntity> : NSRepository<OBJ>