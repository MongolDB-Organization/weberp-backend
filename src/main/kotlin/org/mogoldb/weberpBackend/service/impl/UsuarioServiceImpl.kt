package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.dto.request.UsuarioUpdateDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDetailedDto.Companion.toDetailedDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto
import org.mogoldb.weberpBackend.dto.response.UsuarioDto.Companion.toDto
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.mogoldb.weberpBackend.service.UsuarioService
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

    override fun findAll(): List<UsuarioDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): UsuarioDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
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