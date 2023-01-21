package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.request.EmpresaCreateDto
import org.mogoldb.weberpBackend.dto.request.EmpresaUpdateDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDetailedDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDetailedDto.Companion.toDetailedDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDto
import org.mogoldb.weberpBackend.dto.response.EmpresaDto.Companion.toDto
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.exception.NoPermitionException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.ContratoRepository
import org.mogoldb.weberpBackend.repository.EmpresaRepository
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class EmpresaService(@Autowired private val repository: EmpresaRepository) {

    @Autowired
    private lateinit var contratoRepository: ContratoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserService

    fun findAll(): List<EmpresaDto> {
        return repository.findAll().map<Empresa, EmpresaDto> { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    fun findById(id: Long): EmpresaDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NoPermitionException::class, BadRequestException::class, DuplicateEntryException::class)
    fun create(empresaDto: EmpresaCreateDto): EmpresaDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val contrato = contratoRepository.findById(empresaDto.contratoCodigo!!).getOrNull() ?: throw NotFoundException("Contrato não encontrado")
        if (contrato.usuarioProprietario!!.codigo != loggedUser!!.codigo) {
            if (!loggedUser.administrador) {
                throw NoPermitionException("Somente o proprietário do contrato pode adicionar uma nova empresa")
            }
        }
        if (contrato.licenca == null) {
            throw BadRequestException("Contrato ainda não possui uma licença")
        }
        if (contrato.licenca!!.dataVencimento == null) {
            throw BadRequestException("A licença do contrato não possui uma data de vencimento")
        }
        if (LocalDateTime.now().isAfter(contrato.licenca!!.dataVencimento!!)) {
            throw BadRequestException("Licença do contrato expirada")
        }
        if (contrato.licenca!!.quantidadeEmpresas!! <= repository.buscarQuantidadeEmpresaPorLicenca(contrato.licenca!!.codigo!!)) {
            throw BadRequestException("Limite de empresas cadastradas atingido")
        }
        val empresa = empresaDto.toEntity()
        if (repository.findByCnpj(empresaDto.cnpj!!).isPresent) {
            throw DuplicateEntryException(empresaDto::cnpj.name)
        }
        if (repository.findByInscricaoEstadual(empresaDto.incricaoEstadual!!).isPresent) {
            throw DuplicateEntryException(empresaDto::incricaoEstadual.name)
        }
        empresa.contrato = contrato
        empresa.usuarioCriacao = loggedUser
        empresa.usuarioAtualizacao = loggedUser
        return repository.save(empresa).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    fun update(id: Long, empresaDto: EmpresaUpdateDto): EmpresaDetailedDto {
        var empresa = repository.findById(id).getOrNull() ?: throw NotFoundException()
        var loggedUser = userLoggedUserService.getLoggedUser()
        if (loggedUser!!.codigo!! != empresa.contrato!!.codigo) {
            throw BadRequestException("Somente o proprietário do contrato pode atualizar a empresa")
        }
        if (empresaDto.cnpj != empresa.cnpj && repository.findByCnpj(empresaDto.cnpj!!).isPresent) {
            throw DuplicateEntryException(empresaDto::cnpj.name)
        }
        if (empresaDto.incricaoEstadual != empresa.incricaoEstadual && repository.findByInscricaoEstadual(empresaDto.incricaoEstadual!!).isPresent) {
            throw DuplicateEntryException(empresaDto::incricaoEstadual.name)
        }
        val empresaCodigo = empresa.codigo
        empresa = empresaDto.toEntity(empresa)
        empresa.codigo = empresaCodigo
        return repository.save(empresa).toDetailedDto()
    }
}