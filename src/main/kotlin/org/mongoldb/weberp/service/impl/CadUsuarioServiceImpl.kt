package org.mongoldb.weberp.service.impl

import jakarta.transaction.Transactional
import org.mongoldb.weberp.dto.request.CadUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.CadUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.CadUsuarioDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.CadUsuarioDto
import org.mongoldb.weberp.dto.response.CadUsuarioDto.Companion.toDto
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.CadUsuarioRepository
import org.mongoldb.weberp.service.CadUsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class CadUsuarioServiceImpl : CadUsuarioService {

    @Autowired
    private lateinit var repository: CadUsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    @Transactional
    override fun findAll(): List<CadUsuarioDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun findById(id: Long): CadUsuarioDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun update(id: Long, dto: CadUsuarioUpdateDto): CadUsuarioDetailedDto {
        var loggedUser = userLoggedUserService.getLoggedUser()
        var usuario = repository.findById(id).getOrNull() ?: throw NotFoundException()
        if (usuario.email != dto.email && repository.findByEmail(dto.email!!).isPresent) {
            throw DuplicateEntryException(dto::email.name)
        }
        usuario = dto.toEntity(usuario)
        usuario.cadUsuarioAtualizacao = loggedUser
        return usuario.toDetailedDto()
    }
}