package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CadCidade
import org.springframework.data.jpa.repository.JpaRepository

interface CadCidadeRepository : JpaRepository<CadCidade, Long>