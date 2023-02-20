package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.dto.response.ContratoDetailedDto
import org.mongoldb.weberp.dto.response.ContratoDto
import org.mongoldb.weberp.dto.request.ContratoCreateDto
import org.mongoldb.weberp.dto.request.ContratoUpdateDto
import org.mongoldb.weberp.dto.response.ContratoDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.ContratoDto.Companion.toDto
import org.mongoldb.weberp.exception.BadRequestException
import org.mongoldb.weberp.exception.NoPermitionException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.ContratoRepository
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.ContratoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class ContratoServiceImpl(@Autowired private val repository: ContratoRepository) : ContratoService {

    @Autowired
    private lateinit var sisUsuarioRepository: SisUsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    override fun findAll(): List<ContratoDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NotFoundException::class)
    override fun findById(id: Long): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = repository.findById(id).getOrNull() ?: throw NotFoundException()
        val hasAcesso = sisUsuarioRepository.getContratosCodigos(loggedUser!!.codigo).contains(loggedUser.codigo)
        val isProprietario = contrato.sisUsuarioProprietario!!.codigo == loggedUser.codigo
        val isAdministrador = loggedUser.administrador
        if (!hasAcesso && !isProprietario && !isAdministrador) {
            throw NoPermitionException()
        }
        return contrato.toDetailedDto()
    }

    override fun create(dto: ContratoCreateDto): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = dto.toEntity(null)
        contrato.sisUsuarioProprietario = loggedUser
        contrato.sisUsuarioCriacao = loggedUser
        contrato.sisUsuarioAtualizacao = loggedUser
        contrato.sisUsuarios.add(loggedUser!!)
        return repository.save(contrato).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(BadRequestException::class, NotFoundException::class)
    override fun update(id: Long, dto: ContratoUpdateDto): ContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        var contrato = repository.findById(id).getOrNull() ?: throw NotFoundException()
        if (contrato.sisUsuarioProprietario!!.codigo != loggedUser!!.codigo) {
            throw BadRequestException("Contrato só pode ser editado pelo proprietário")
        }
        contrato = dto.toEntity(contrato)
        contrato.sisUsuarioAtualizacao = loggedUser
        return repository.save(contrato).toDetailedDto()
    }
}