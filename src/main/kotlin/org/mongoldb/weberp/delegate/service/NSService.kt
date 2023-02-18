package org.mongoldb.weberp.delegate.service

import jakarta.transaction.Transactional
import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.CadUsuario
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.CadUsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.jvm.Throws

abstract class NSService<OB : NSEntity>(@Autowired private val repository: NSRepository<OB>) {

    enum class NSServiceSaveType {
        CREATE, UPDATE
    }

    @Autowired
    lateinit var cadUsuarioRepository: CadUsuarioRepository

    fun getLoggedUser(): CadUsuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val emailQueryResult = cadUsuarioRepository.findByEmail(email)
        if (!emailQueryResult.isPresent) return null
        return cadUsuarioRepository.findByEmail(email).get()
    }

    @Transactional
    open fun findAll(): List<OB> = repository.findAll()

    open fun findById(id: Long): OB? {
        val result = repository.findById(id)
        if (!result.isPresent) {
            return null
        }
        return result.get()
    }

    open fun create(obj: OB): OB {
        afterCreateAndUpdate(obj, null, NSServiceSaveType.CREATE)
        return repository.save(obj)
    }

    open fun update(obj: OB, idObject: Long): OB {
        afterCreateAndUpdate(obj, idObject, NSServiceSaveType.UPDATE)
        obj.codigo = idObject
        return repository.save(obj)
    }

    open fun deleteById(id: Long) = repository.deleteById(id)

    @Throws(NotFoundException::class)
    open fun afterCreateAndUpdate(obj: OB, idUpdate: Long?, saveType: NSServiceSaveType) {
        val loggedUser = getLoggedUser()
        if (saveType == NSServiceSaveType.CREATE) {
            obj.cadUsuarioCriacao = loggedUser
            obj.cadUsuarioAtualizacao = loggedUser
        } else if (saveType == NSServiceSaveType.UPDATE) {
            if (!repository.findById(idUpdate!!).isPresent) {
                throw NotFoundException()
            }
            obj.cadUsuarioAtualizacao = loggedUser
        }
    }
}