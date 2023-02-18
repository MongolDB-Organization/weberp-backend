package org.mongoldb.weberp.repository;

import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.CadUsuario
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CadUsuarioRepository : NSRepository<CadUsuario> {

    @Query("select u from CadUsuario u where u.email = :email")
    fun findByEmail(@Param("email") email: String): Optional<CadUsuario>

    @Query("select uc.sis_contrato_codigo from sis_contrato_usuario as uc where uc.cad_usuario_codigo = :codigo", nativeQuery = true)
    fun getContratosCodigos(codigo: Long) : List<Long>
}