package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.config.PasswordEncoderConfig
import org.mogoldb.weberpBackend.dto.request.UsuarioCreateUpdateDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto
import org.mogoldb.weberpBackend.delegate.service.NSTService
import org.mogoldb.weberpBackend.dto.mapper.toDetailedDto
import org.mogoldb.weberpBackend.dto.mapper.toDto
import org.mogoldb.weberpBackend.dto.mapper.toEntity
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UsuarioService(@Autowired val repository: UsuarioRepository) : NSTService<Usuario, UsuarioDto, UsuarioDetailedDto, UsuarioCreateUpdateDto>(repository) {

    @Autowired
    private lateinit var passwordEncoderConfiguration: PasswordEncoderConfig

    override fun toDto(entity: Usuario): UsuarioDto = entity.toDto()

    override fun toDetailedDto(entity: Usuario): UsuarioDetailedDto = entity.toDetailedDto()

    override fun toEntity(dto: UsuarioCreateUpdateDto, mergeEntity: Usuario?): Usuario = dto.toEntity(mergeEntity)

    fun findByEmail(email: String): Usuario? = repository.findByEmail(email).map { item ->
        item
    }.orElse(null)

    fun getContratosCodigos(usuario: Usuario): List<Long> {
        return repository.getContratosCodigos(usuario.codigo)
    }

    @Throws(DuplicateEntryException::class)
    override fun beforeCreateAndUpdate(obj: Usuario, idUpdate: Long?, saveType: NSTServiceSaveType) {
        val queryEmail = findByEmail(obj.email!!)
        if (queryEmail != null && queryEmail.codigo != idUpdate) {
            throw DuplicateEntryException(obj::email.name)
        }
        obj.senha = passwordEncoderConfiguration.passwordEncoder()!!.encode(obj.senha!!)
        super.beforeCreateAndUpdate(obj, idUpdate, saveType)
    }
}
