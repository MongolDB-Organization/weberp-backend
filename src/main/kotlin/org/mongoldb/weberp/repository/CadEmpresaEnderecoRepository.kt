package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CadEmpresaEndereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CadEmpresaEnderecoRepository : JpaRepository<CadEmpresaEndereco, Long>