package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CadEndereco
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CadEnderecoRepository : JpaRepository<CadEndereco, Long>