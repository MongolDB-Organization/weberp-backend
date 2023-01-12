package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.service.NSContratoLevelService
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.EmpresaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.Throws

@Service
class EmpresaService(@Autowired private val repository: EmpresaRepository) : NSContratoLevelService<Empresa>(repository) {

    @Autowired
    private lateinit var contratoService: ContratoService

    fun findByCnpj(cnpj: String): Empresa? {
        return repository.findByCnpj(cnpj).map { item ->
            item
        }.orElse(null)
    }

    fun findByInscricaoEstadual(inscricaoEstadual: String): Empresa? {
        return repository.findByInscricaoEstadual(inscricaoEstadual).map { item ->
            item
        }.orElse(null)
    }

    fun buscarQuantidadeEmpresasPorLicenca(codigoContrato: Long): Long {
        return repository.buscarQuantidadeEmpresaPorLicenca(codigoContrato)
    }

    @Throws(DuplicateEntryException::class)
    override fun afterCreateAndUpdate(obj: Empresa, idUpdate: Long?, saveType: NSServiceSaveType) {
        val empresaCnpj = findByCnpj(obj.cnpj!!)
        if (empresaCnpj != null && empresaCnpj.codigo != idUpdate) {
            throw DuplicateEntryException(obj::cnpj.name)
        }
        val empresaInsc = findByInscricaoEstadual(obj.incricaoEstadual!!)
        if (empresaInsc != null && empresaInsc.codigo != idUpdate) {
            throw DuplicateEntryException(obj::incricaoEstadual.name)
        }
        super.afterCreateAndUpdate(obj, idUpdate, saveType)
    }

    override fun create(obj: Empresa): Empresa {
        val contrato = contratoService.findById(obj.contrato!!.codigo)
            ?: throw NotFoundException("Contrato não encontrado")
        val licenca = contrato.licenca ?: throw BadRequestException("Contrato ainda não possui uma licença")
        val dataVencimentoContrato = licenca.dataVencimento ?: throw BadRequestException("A lincença do contrato não possui uma data de vencimento")
        if (LocalDateTime.now().isAfter(dataVencimentoContrato)) {
            throw BadRequestException("Licença expirada")
        }
        if (licenca.quantidadeEmpresas!! >= buscarQuantidadeEmpresasPorLicenca(licenca.codigo).toInt()) {
            throw BadRequestException("Limite de empresas cadastradas atingido")
        }
        return super.create(obj)
    }
}