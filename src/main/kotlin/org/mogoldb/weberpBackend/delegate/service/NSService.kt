package org.mogoldb.weberpBackend.delegate.service

import jakarta.annotation.PostConstruct
import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import org.mogoldb.weberpBackend.delegate.repository.NSRepository
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder

abstract class NSService<OB : NSEntity>(@Autowired private val repository: NSRepository<OB>) {

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @PostConstruct
    private fun initialize() {
        usuarioRepository.flush()
    }

    fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val emailQueryResult = usuarioRepository.findByEmail(email)
        if (!emailQueryResult.isPresent) return null
        return usuarioRepository.findByEmail(email).get()
    }

    open fun findAll(): List<OB> = repository.findAll()

    open fun findById(id: Long): OB? {
        val result = repository.findById(id)
        if (!result.isPresent) return null
        return result.get()
    }

    open fun save(obj: OB): OB {
        return repository.save(obj)
    }

    open fun save(obj: OB, id: Long? = null): OB {
        if (id != null) obj.codigo = id
        val loggedUser = getLoggedUser()
        if (loggedUser != null) {
            obj.usuarioAtualizacao = loggedUser
            if (obj.codigo.toInt() == 0 && id == null) {
                obj.usuarioCriacao = loggedUser
            }
        }
        return repository.save(obj)
    }

    open fun save(obj: OB, id: Long? = null, exec: Usuario): OB {
        obj.usuarioAtualizacao = exec
        if (obj.codigo == null && id == null) {
            obj.usuarioCriacao = exec
        }
        return save(obj, id)
    }

    open fun deleteById(id: Long) = repository.deleteById(id)
}