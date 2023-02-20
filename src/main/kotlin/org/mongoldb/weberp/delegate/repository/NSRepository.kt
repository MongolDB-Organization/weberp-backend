package org.mongoldb.weberp.delegate.repository

import org.mongoldb.weberp.delegate.entity.NSEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface NSRepository<OBJ: NSEntity> : JpaRepository<OBJ, Long> {

    override fun findAll(): MutableList<OBJ>
}