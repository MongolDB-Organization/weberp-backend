package org.mogoldb.weberpBackend.delegate.service

import jakarta.transaction.Transactional
import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.repository.NSRepository
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import kotlin.jvm.Throws

abstract class NSService<OB : NSEntity>(@Autowired private val repository: NSRepository<OB>) {

    enum class NSServiceSaveType {
        CREATE, UPDATE
    }

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val emailQueryResult = usuarioRepository.findByEmail(email)
        if (!emailQueryResult.isPresent) return null
        return usuarioRepository.findByEmail(email).get()
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
            obj.usuarioCriacao = loggedUser
            obj.usuarioAtualizacao = loggedUser
        } else if (saveType == NSServiceSaveType.UPDATE) {
            if (!repository.findById(idUpdate!!).isPresent) {
                throw NotFoundException()
            }
            obj.usuarioAtualizacao = loggedUser
        }
    }
}