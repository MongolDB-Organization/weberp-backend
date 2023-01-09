package org.mogoldb.weberpBackend.repository;

import org.mogoldb.weberpBackend.entity.Empresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface EmpresaRepository : JpaRepository<Empresa, Long> {

    @Query("select e from Empresa e where e.cnpj = :cnpj")
    fun findByCnpj(@Param("cnpj") cnpj: String): Optional<Empresa>

    @Query("select e from Empresa e where e.incricaoEstadual = :inscricaoEstadual")
    fun findByInscricaoEstadual(@Param("inscricaoEstadual") inscricaoEstadual: String): Optional<Empresa>

    @Query("select count(e) from Empresa e where e.contrato.licenca.codigo = :codigoLicenca")
    fun buscarQuantidadeEmpresaPorLicenca(@Param("codigoLicenca") codigoLicenca: Long): Long
}