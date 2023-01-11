package org.mogoldb.weberpBackend.delegate.entity

import org.mogoldb.weberpBackend.entity.Usuario
import java.time.LocalDateTime

interface NSEntity {
    var codigo: Long;
    var usuarioAtualizacao: Usuario?
    var usuarioCriacao: Usuario?
    var dataCriacao: LocalDateTime?
    var dataModificacao: LocalDateTime?
}