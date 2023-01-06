package org.mogoldb.weberpBackend.repository;

import org.mogoldb.weberpBackend.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<Usuario>
}