package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.service.NSContratoLevelService
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.repository.EmpresaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class EmpresaService(@Autowired private val repository: EmpresaRepository) : NSContratoLevelService<Empresa>(repository) {

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
}