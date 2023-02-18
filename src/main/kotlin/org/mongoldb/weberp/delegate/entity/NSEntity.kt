package org.mongoldb.weberp.delegate.entity

import org.mongoldb.weberp.entity.CadUsuario
import java.time.LocalDateTime

interface NSEntity {
    var codigo: Long;
    var cadUsuarioAtualizacao: CadUsuario?
    var cadUsuarioCriacao: CadUsuario?
    var dataCriacao: LocalDateTime?
    var dataModificacao: LocalDateTime?
}