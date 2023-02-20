package org.mongoldb.weberp.delegate.service

import jakarta.transaction.Transactional
import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.SisUsuario
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.jvm.Throws

abstract class NSService<OB : NSEntity>(@Autowired private val repository: NSRepository<OB>) {

    enum class NSServiceSaveType {
        CREATE, UPDATE
    }

    @Autowired
    lateinit var sisUsuarioRepository: SisUsuarioRepository

    fun getLoggedUser(): SisUsuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val emailQueryResult = sisUsuarioRepository.findByEmail(email)
        if (!emailQueryResult.isPresent) return null
        return sisUsuarioRepository.findByEmail(email).get()
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
            obj.sisUsuarioCriacao = loggedUser
            obj.sisUsuarioAtualizacao = loggedUser
        } else if (saveType == NSServiceSaveType.UPDATE) {
            if (!repository.findById(idUpdate!!).isPresent) {
                throw NotFoundException()
            }
            obj.sisUsuarioAtualizacao = loggedUser
        }
    }
}