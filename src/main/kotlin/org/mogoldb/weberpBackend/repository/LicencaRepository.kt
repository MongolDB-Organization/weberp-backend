package org.mogoldb.weberpBackend.repository;

import org.mogoldb.weberpBackend.entity.Licenca
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LicencaRepository : JpaRepository<Licenca, Long>