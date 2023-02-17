package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.Usuario
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