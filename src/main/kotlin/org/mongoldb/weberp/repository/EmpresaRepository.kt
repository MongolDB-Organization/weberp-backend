package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.delegate.repository.NSContratoLevelRepository
import org.mongoldb.weberp.entity.Empresa
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface EmpresaRepository : NSContratoLevelRepository<Empresa> {

    @Query("select e from Empresa e where e.cnpj = :cnpj")
    fun findByCnpj(@Param("cnpj") cnpj: String): Optional<Empresa>

    @Query("select e from Empresa e where e.incricaoEstadual = :inscricaoEstadual")
    fun findByInscricaoEstadual(@Param("inscricaoEstadual") inscricaoEstadual: String): Optional<Empresa>

    @Query("select count(e) from Empresa e where e.contrato.licenca.codigo = :codigoLicenca")
    fun buscarQuantidadeEmpresaPorLicenca(@Param("codigoLicenca") codigoLicenca: Long): Long
}