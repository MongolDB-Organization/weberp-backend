package org.mongoldb.weberp.service.impl

import jakarta.transaction.Transactional
import org.mongoldb.weberp.dto.request.CadEmpresaCreateDto
import org.mongoldb.weberp.dto.request.CadEmpresaUpdateDto
import org.mongoldb.weberp.dto.response.CadEmpresaDetailedDto
import org.mongoldb.weberp.dto.response.CadEmpresaDetailedDto.Companion.toDetailedDto
import org.mongoldb.weberp.dto.response.CadEmpresaDto
import org.mongoldb.weberp.dto.response.CadEmpresaDto.Companion.toDto
import org.mongoldb.weberp.entity.CadEmpresa
import org.mongoldb.weberp.exception.BadRequestException
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NoPermitionException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.repository.*
import org.mongoldb.weberp.service.CadEmpresaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.Throws
import kotlin.jvm.optionals.getOrNull

@Service
class CadEmpresaServiceImpl(@Autowired private val repository: CadEmpresaRepository) : CadEmpresaService {

    @Autowired
    private lateinit var sisContratoRepository: SisContratoRepository

    @Autowired
    private lateinit var userLoggedUserService: LoggedUserServiceImpl

    @Autowired
    private lateinit var cadEmpresaEnderecoRepository: CadEmpresaEnderecoRepository

    @Autowired
    private lateinit var cadEnderecoRepository: CadEnderecoRepository

    @Autowired
    private lateinit var cadCidadeRepository: CadCidadeRepository

    @Transactional
    override fun findAll(): List<CadEmpresaDto> {
        return repository.findAll().map<CadEmpresa, CadEmpresaDto> { it -> it.toDto() }
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class)
    @Transactional
    override fun findById(id: Long): CadEmpresaDetailedDto {
        return (repository.findById(id).getOrNull() ?: throw NotFoundException()).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NoPermitionException::class, BadRequestException::class, DuplicateEntryException::class)
    @Transactional
    override fun create(dto: CadEmpresaCreateDto): CadEmpresaDetailedDto {
        val loggedUser = userLoggedUserService.getLoggedUser()
        val sisContrato = sisContratoRepository.findById(dto.sisContratoCodigo!!).getOrNull() ?: throw NotFoundException("Contrato não encontrado")

        if (sisContrato.sisUsuarioProprietario!!.codigo != loggedUser!!.codigo) {
            if (!loggedUser.administrador) {
                throw NoPermitionException("Somente o proprietário do contrato pode adicionar uma nova empresa")
            }
        }

        if (sisContrato.sisLicenca == null) {
            throw BadRequestException("Contrato não possui uma licença")
        }

        if (sisContrato.sisLicenca!!.dataVencimento == null) {
            throw BadRequestException("A licença do contrato não possui uma data de vencimento")
        }

        if (LocalDateTime.now().isAfter(sisContrato.sisLicenca!!.dataVencimento!!)) {
            throw BadRequestException("Licença do contrato expirada")
        }

        if (sisContrato.sisLicenca!!.quantidadeEmpresas!! <= repository.buscarQuantidadeEmpresaPorLicenca(sisContrato.sisLicenca!!.codigo)) {
            throw BadRequestException("Limite de empresas cadastradas atingido")
        }


        if (repository.findByCnpj(dto.cnpj!!).isPresent) {
            throw DuplicateEntryException(dto::cnpj.name)
        }

        if (repository.findByInscricaoEstadual(dto.incricaoEstadual!!).isPresent) {
            throw DuplicateEntryException(dto::incricaoEstadual.name)
        }

        val cadEmpresa = dto.toEntity()
        val cadEmpresaEnderecos = cadEmpresa.cadEmpresaEnderecos

        cadEmpresa.cadEmpresaEnderecos = mutableListOf()
        repository.save(cadEmpresa)

        for (end in cadEmpresaEnderecos) {
            end.cadEmpresa = cadEmpresa
            end.sisUsuarioAtualizacao = loggedUser
            end.cadEndereco?.sisUsuarioAtualizacao = loggedUser
            end.cadEndereco?.cadCidade = cadCidadeRepository.findById(end.cadEndereco!!.cadCidade!!.codigo!!).getOrNull() ?: throw NotFoundException("Cidade não encontrada")

            if (end.codigo == null) {
                end.sisUsuarioCriacao = loggedUser
            }

            if (end.cadEndereco?.codigo == null) {
                end.cadEndereco?.sisUsuarioCriacao = loggedUser
            }
        }

        cadEmpresa.sisContrato = sisContrato
        cadEmpresa.sisUsuarioCriacao = loggedUser
        cadEmpresa.sisUsuarioAtualizacao = loggedUser
        cadEmpresa.cadEmpresaEnderecos = cadEmpresaEnderecos

        return repository.save(cadEmpresa).toDetailedDto()
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Throws(NotFoundException::class, NoPermitionException::class, DuplicateEntryException::class)
    @Transactional
    override fun update(id: Long, dto: CadEmpresaUpdateDto): CadEmpresaDetailedDto {
        var cadEmpresa = repository.findById(id).getOrNull() ?: throw NotFoundException()
        val loggedUser = userLoggedUserService.getLoggedUser()

        if (loggedUser!!.codigo != cadEmpresa.sisContrato!!.sisUsuarioProprietario!!.codigo) {
            throw NoPermitionException("Somente o proprietário do contrato pode atualizar a empresa")
        }

        if (dto.cnpj != cadEmpresa.cnpj && repository.findByCnpj(dto.cnpj!!).isPresent) {
            throw DuplicateEntryException(dto::cnpj.name)
        }

        if (dto.incricaoEstadual != cadEmpresa.incricaoEstadual && repository.findByInscricaoEstadual(dto.incricaoEstadual!!).isPresent) {
            throw DuplicateEntryException(dto::incricaoEstadual.name)
        }

        val empresaCodigo = cadEmpresa.codigo

        cadEmpresa = dto.toEntity(cadEmpresa)
        cadEmpresa.codigo = empresaCodigo

        for (end in cadEmpresa.cadEmpresaEnderecos) {
            end.cadEmpresa = cadEmpresa
            end.sisUsuarioAtualizacao = loggedUser
            end.cadEndereco?.sisUsuarioAtualizacao = loggedUser
            end.cadEndereco?.cadCidade = cadCidadeRepository.findById(end.cadEndereco!!.cadCidade!!.codigo!!).getOrNull() ?: throw NotFoundException("Cidade não encontrada")

            if (end.codigo == null) {
                end.sisUsuarioCriacao = loggedUser
            }

            if (end.cadEndereco?.codigo == null) {
                end.cadEndereco?.sisUsuarioCriacao = loggedUser
            }
        }

        return repository.save(cadEmpresa).toDetailedDto()
    }
}