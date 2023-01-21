package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto
import org.mogoldb.weberpBackend.dto.request.ContratoCreateDto
import org.mogoldb.weberpBackend.dto.request.ContratoUpdateDto
import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto.Companion.toDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto.Companion.toDto
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NoPermitionException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.ContratoRepository
import org.mogoldb.weberpBackend.service.ContratoService
import org.mogoldb.weberpBackend.service.LoggedUserService
import org.mogoldb.weberpBackend.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class ContratoServiceImpl(@Autowired private val repository: ContratoRepository) : ContratoService {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserService

    override fun findAll(): List<ContratoDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NotFoundException::class)
    override fun findById(id: Long): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = repository.findById(id).getOrNull() ?: throw NotFoundException()
        val hasAcesso = usuarioService.getContratosCodigos(loggedUser!!).contains(loggedUser.codigo)
        val isProprietario = contrato.usuarioProprietario!!.codigo == loggedUser.codigo
        val isAdministrador = loggedUser.administrador
        if (!hasAcesso && !isProprietario && !isAdministrador) {
            throw NoPermitionException()
        }
        return contrato.toDetailedDto()
    }

    override fun create(dto: ContratoCreateDto): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = dto.toEntity(null)
        contrato.usuarioProprietario = loggedUser
        contrato.usuarioCriacao = loggedUser
        contrato.usuarioAtualizacao = loggedUser
        contrato.usuarios.add(loggedUser!!)
        return repository.save(contrato).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(BadRequestException::class, NotFoundException::class)
    override fun update(id: Long, dto: ContratoUpdateDto): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        var contrato = repository.findById(id).getOrNull() ?: throw NotFoundException()
        if (contrato.usuarioProprietario!!.codigo != loggedUser!!.codigo) {
            throw BadRequestException("Contrato só pode ser editado pelo proprietário")
        }
        contrato = dto.toEntity(contrato)
        contrato.usuarioAtualizacao = loggedUser
        return repository.save(contrato).toDetailedDto()
    }
}