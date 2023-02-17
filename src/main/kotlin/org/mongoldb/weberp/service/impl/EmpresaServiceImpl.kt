package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.dto.request.EmpresaCreateDto
import org.mongoldb.weberp.dto.request.EmpresaUpdateDto
import org.mongoldb.weberp.dto.response.EmpresaDetailedDto
import org.mongoldb.weberp.dto.response.EmpresaDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.EmpresaDto
import org.mongoldb.weberp.dto.response.EmpresaDto.Companion.toDto
import org.mongoldb.weberp.entity.Empresa
import org.mongoldb.weberp.exception.BadRequestException
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NoPermitionException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.ContratoRepository
import org.mongoldb.weberp.repository.EmpresaRepository
import org.mongoldb.weberp.repository.UsuarioRepository
import org.mongoldb.weberp.service.EmpresaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class EmpresaServiceImpl(@Autowired private val repository: EmpresaRepository) : EmpresaService {

    @Autowired
    private lateinit var contratoRepository: ContratoRepository

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    override fun findAll(): List<EmpresaDto> {
        return repository.findAll().map<Empresa, EmpresaDto> { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    override fun findById(id: Long): EmpresaDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NoPermitionException::class, BadRequestException::class, DuplicateEntryException::class)
    override fun create(empresaDto: EmpresaCreateDto): EmpresaDetailedDto {
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
        if (contrato.licenca!!.quantidadeEmpresas!! <= repository.buscarQuantidadeEmpresaPorLicenca(contrato.licenca!!.codigo)) {
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
    @Throws(NotFoundException::class, NoPermitionException::class, DuplicateEntryException::class)
    override fun update(id: Long, dto: EmpresaUpdateDto): EmpresaDetailedDto {
        var empresa = repository.findById(id).getOrNull() ?: throw NotFoundException()
        val loggedUser = userLoggedUserService.getLoggedUser()
        if (loggedUser!!.codigo != empresa.contrato!!.codigo) {
            throw NoPermitionException("Somente o proprietário do contrato pode atualizar a empresa")
        }
        if (dto.cnpj != empresa.cnpj && repository.findByCnpj(dto.cnpj!!).isPresent) {
            throw DuplicateEntryException(dto::cnpj.name)
        }
        if (dto.incricaoEstadual != empresa.incricaoEstadual && repository.findByInscricaoEstadual(dto.incricaoEstadual!!).isPresent) {
            throw DuplicateEntryException(dto::incricaoEstadual.name)
        }
        val empresaCodigo = empresa.codigo
        empresa = dto.toEntity(empresa)
        empresa.codigo = empresaCodigo
        return repository.save(empresa).toDetailedDto()
    }
}