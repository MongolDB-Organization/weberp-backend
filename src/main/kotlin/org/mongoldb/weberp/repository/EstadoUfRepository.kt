package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.entity.CadEstado
import org.springframework.data.jpa.repository.JpaRepository

interface EstadoUfRepository : JpaRepository<CadEstado, Long>