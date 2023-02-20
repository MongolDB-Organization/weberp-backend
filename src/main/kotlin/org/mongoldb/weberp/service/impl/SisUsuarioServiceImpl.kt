package org.mongoldb.weberp.service.impl

import jakarta.transaction.Transactional
import org.mongoldb.weberp.dto.request.SisUsuarioUpdateDto
import org.mongoldb.weberp.dto.response.SisUsuarioDetailedDto
import org.mongoldb.weberp.dto.response.SisUsuarioDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto
import org.mongoldb.weberp.dto.response.SisUsuarioDto.Companion.toDto
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.SisUsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class SisUsuarioServiceImpl : SisUsuarioService {

    @Autowired
    private lateinit var repository: SisUsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    @Transactional
    override fun findAll(): List<SisUsuarioDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun findById(id: Long): SisUsuarioDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun update(id: Long, dto: SisUsuarioUpdateDto): SisUsuarioDetailedDto {
        var loggedUser = userLoggedUserService.getLoggedUser()
        var usuario = repository.findById(id).getOrNull() ?: throw NotFoundException()
        if (usuario.email != dto.email && repository.findByEmail(dto.email!!).isPresent) {
            throw DuplicateEntryException(dto::email.name)
        }
        usuario = dto.toEntity(usuario)
        usuario.sisUsuarioAtualizacao = loggedUser
        return usuario.toDetailedDto()
    }
}