package org.mogoldb.weberpBackend.repository;

import org.mogoldb.weberpBackend.delegate.repository.NSRepository
import org.mogoldb.weberpBackend.entity.Usuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UsuarioRepository : NSRepository<Usuario> {

    @Query("select u from Usuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<Usuario>

    @Query("select uc.usuarios_codigo from usuarios_contratos as uc where uc.usuarios_codigo = :codigo", nativeQuery = true)
    fun getContratosCodigos(codigo: Long) : List<Long>
}