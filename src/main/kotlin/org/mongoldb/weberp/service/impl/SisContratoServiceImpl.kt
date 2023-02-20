package org.mongoldb.weberp.service.impl

import jakarta.transaction.Transactional
import org.mongoldb.weberp.dto.response.SisContratoDetailedDto
import org.mongoldb.weberp.dto.response.SisContratoDto
import org.mongoldb.weberp.dto.request.SisContratoCreateDto
import org.mongoldb.weberp.dto.request.SisContratoUpdateDto
import org.mongoldb.weberp.dto.response.SisContratoDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.SisContratoDto.Companion.toDto
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.exception.BadRequestException
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NoPermitionException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.SisContratoRepository
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.SisContratoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class SisContratoServiceImpl(@Autowired private val repository: SisContratoRepository) : SisContratoService {

    @Autowired
    private lateinit var sisUsuarioRepository: SisUsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    override fun findAll(): List<SisContratoDto> {
        return repository.findAll().map { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NotFoundException::class)
    @Transactional
    override fun findById(id: Long): SisContratoDetailedDto {
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

    @Transactional
    override fun create(dto: SisContratoCreateDto): SisContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = dto.toEntity(null)

        if (repository.findByNome(contrato.nome) != null) {
            throw DuplicateEntryException(SisContrato::nome.name)
        }

        contrato.sisUsuarioProprietario = loggedUser
        contrato.sisUsuarioCriacao = loggedUser
        contrato.sisUsuarioAtualizacao = loggedUser
        contrato.sisUsuarios.add(loggedUser!!)

        return repository.save(contrato).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(BadRequestException::class, NotFoundException::class)
    @Transactional
    override fun update(id: Long, dto: SisContratoUpdateDto): SisContratoDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        var contrato = repository.findById(id).getOrNull() ?: throw NotFoundException()

        if (contrato.sisUsuarioProprietario!!.codigo != loggedUser!!.codigo) {
            throw BadRequestException("Contrato só pode ser editado pelo proprietário")
        }

        val contratoByNome = repository.findByNome(dto.nome!!);

        if (contratoByNome != null && contratoByNome.codigo != id) {
            throw DuplicateEntryException(SisContrato::nome.name)
        }

        contrato = dto.toEntity(contrato)
        contrato.sisUsuarioAtualizacao = loggedUser

        return repository.save(contrato).toDetailedDto()
    }
}