package org.mongoldb.weberp.service.impl

import jakarta.transaction.Transactional
import org.mongoldb.weberp.dto.request.UsuarioUpdateDto
import org.mongoldb.weberp.dto.response.UsuarioDetailedDto
import org.mongoldb.weberp.dto.response.UsuarioDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.UsuarioDto
import org.mongoldb.weberp.dto.response.UsuarioDto.Companion.toDto
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.UsuarioRepository
import org.mongoldb.weberp.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class UsuarioServiceImpl : UsuarioService {

    @Autowired
    private lateinit var repository: UsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    @Transactional
    override fun findAll(): List<UsuarioDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun findById(id: Long): UsuarioDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun update(id: Long, dto: UsuarioUpdateDto): UsuarioDetailedDto {
        var loggedUser = userLoggedUserService.getLoggedUser()
        var usuario = repository.findById(id).getOrNull() ?: throw NotFoundException()
        if (usuario.email != dto.email && repository.findByEmail(dto.email!!).isPresent) {
            throw DuplicateEntryException(dto::email.name)
        }
        usuario = dto.toEntity(usuario)
        usuario.usuarioAtualizacao = loggedUser
        return usuario.toDetailedDto()
    }
}