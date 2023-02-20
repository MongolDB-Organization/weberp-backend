package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.delegate.repository.NSContratoLevelRepository
import org.mongoldb.weberp.entity.CadEmpresa
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface CadEmpresaRepository : NSContratoLevelRepository<CadEmpresa> {

    @Query("select e from CadEmpresa e where e.cnpj = :cnpj")
    fun findByCnpj(@Param("cnpj") cnpj: String): Optional<CadEmpresa>

    @Query("select e from CadEmpresa e where e.incricaoEstadual = :inscricaoEstadual")
    fun findByInscricaoEstadual(@Param("inscricaoEstadual") inscricaoEstadual: String): Optional<CadEmpresa>

    @Query("select count(e) from CadEmpresa e where e.sisContrato.licenca.codigo = :codigoLicenca")
    fun buscarQuantidadeEmpresaPorLicenca(@Param("codigoLicenca") codigoLicenca: Long): Long
}