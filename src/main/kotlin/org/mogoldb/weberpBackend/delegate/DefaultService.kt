package org.mogoldb.weberpBackend.delegate

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

abstract class DefaultService<OB : DefaultEntity, PK : Long>(private val repository: JpaRepository<OB, PK>) {

    open fun findAll(): List<OB> =
        repository.findAll()

    open fun findById(id: PK): Optional<OB> =
        repository.findById(id)

    open fun save(obj: OB, id: PK? = null): OB {
        if (id != null)
            obj.codigo = id
        return repository.save(obj)
    }

    open fun deleteById(id: PK) =
        repository.deleteById(id)
}