package org.mongoldb.weberp.delegate.entity

import org.mongoldb.weberp.entity.Usuario
import java.time.LocalDateTime

interface NSEntity {
    var codigo: Long;
    var usuarioAtualizacao: Usuario?
    var usuarioCriacao: Usuario?
    var dataCriacao: LocalDateTime?
    var dataModificacao: LocalDateTime?
}