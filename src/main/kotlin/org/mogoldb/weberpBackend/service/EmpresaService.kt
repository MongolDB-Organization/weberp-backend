package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.DefaultService
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.repository.EmpresaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmpresaService(@Autowired private val repository: EmpresaRepository) : DefaultService<Empresa, Long>(repository) {

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

    override fun save(obj: Empresa, id: Long?): Empresa {
        val empresaCnpj = findByCnpj(obj.cnpj!!)
        if (empresaCnpj != null && empresaCnpj.codigo != id) {
            throw DuplicateEntryException(obj::cnpj.name)
        }
        val empresaInsc = findByInscricaoEstadual(obj.incricaoEstadual!!)
        if (empresaInsc != null && empresaInsc.codigo != id) {
            throw DuplicateEntryException(obj::incricaoEstadual.name)
        }
        return super.save(obj, id)
    }

    fun buscarQuantidadeEmpresasPorLicenca(codigoContrato: Long): Long {
        return repository.buscarQuantidadeEmpresaPorLicenca(codigoContrato)
    }
}