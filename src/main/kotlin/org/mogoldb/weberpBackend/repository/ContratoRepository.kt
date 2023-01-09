package org.mogoldb.weberpBackend.repository

import org.mogoldb.weberpBackend.entity.Contrato
import org.springframework.data.jpa.repository.JpaRepository

interface ContratoRepository : JpaRepository<Contrato, Long>