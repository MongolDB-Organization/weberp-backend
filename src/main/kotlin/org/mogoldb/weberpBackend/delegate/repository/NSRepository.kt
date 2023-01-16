package org.mogoldb.weberpBackend.delegate.repository

import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface NSRepository<OBJ: NSEntity> : JpaRepository<OBJ, Long> {

    override fun findAll(): MutableList<OBJ>
}