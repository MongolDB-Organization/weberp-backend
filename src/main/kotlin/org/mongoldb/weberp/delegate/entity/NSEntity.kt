package org.mongoldb.weberp.delegate.entity

import org.mongoldb.weberp.entity.SisUsuario
import java.time.LocalDateTime

interface NSEntity {
    var codigo: Long;
    var sisUsuarioAtualizacao: SisUsuario?
    var sisUsuarioCriacao: SisUsuario?
    var dataCriacao: LocalDateTime?
    var dataModificacao: LocalDateTime?
}