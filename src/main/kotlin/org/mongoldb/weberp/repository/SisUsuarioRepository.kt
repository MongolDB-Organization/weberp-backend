package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.SisUsuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SisUsuarioRepository : NSRepository<SisUsuario> {

    @Query("select u from SisUsuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<SisUsuario>

    @Query("select uc.sis_contrato_codigo from sis_contrato_usuario as uc where uc.sis_usuario_codigo = :codigo", nativeQuery = true)
    fun getContratosCodigos(codigo: Long) : List<Long>
}