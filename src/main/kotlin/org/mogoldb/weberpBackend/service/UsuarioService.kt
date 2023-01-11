package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.config.PasswordEncoderConfig
import org.mogoldb.weberpBackend.delegate.service.NSService
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UsuarioService(@Autowired val repository: UsuarioRepository) : NSService<Usuario>(repository) {

    @Autowired
    private lateinit var passwordEncoderConfiguration: PasswordEncoderConfig

    fun findByEmail(email: String): Usuario? = repository.findByEmail(email).map { item ->
        item
    }.orElse(null)

    fun getContratosCodigos(usuario: Usuario): List<Long> {
        return repository.getContratosCodigos(usuario.codigo)
    }

    @Throws(DuplicateEntryException::class)
    override fun afterCreateAndUpdate(obj: Usuario, idUpdate: Long?, saveType: NSServiceSaveType) {
        val queryEmail = findByEmail(obj.email!!)
        if (queryEmail != null && queryEmail.codigo != idUpdate) {
            throw DuplicateEntryException(obj::email.name)
        }
        obj.senha = passwordEncoderConfiguration.passwordEncoder()!!.encode(obj.senha!!)
        super.afterCreateAndUpdate(obj, idUpdate, saveType)
    }
}