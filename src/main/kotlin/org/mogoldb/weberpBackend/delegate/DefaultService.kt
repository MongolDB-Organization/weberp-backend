package org.mogoldb.weberpBackend.delegate

import jakarta.annotation.PostConstruct
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

abstract class DefaultService<OB : DefaultEntity, PK : Long>(private val repository: JpaRepository<OB, PK>) {

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @PostConstruct
    fun initialize() {
        usuarioRepository.flush()
    }

    private fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder
            .getContext()
            .authentication
            .name
        val emailQueryResult = usuarioRepository.findByEmail(email)
        if (!emailQueryResult.isPresent)
            return null
        return usuarioRepository.findByEmail(email).get()
    }

    open fun findAll(): List<OB> = repository.findAll()

    open fun findById(id: PK): OB? {
        val result = repository.findById(id)
        if (!result.isPresent)
            return null
        return result.get()
    }

    open fun save(obj: OB): OB {
        return repository.save(obj)
    }

    open fun save(obj: OB, id: PK? = null): OB {
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

    open fun save(obj: OB, id: PK? = null, exec: Usuario): OB {
        obj.usuarioAtualizacao = exec
        if (obj.codigo == null && id == null) {
            obj.usuarioCriacao = exec
        }
        return save(obj, id)
    }

    open fun deleteById(id: PK) = repository.deleteById(id)
}