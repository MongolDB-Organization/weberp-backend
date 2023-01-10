package org.mogoldb.weberpBackend.delegate.entity

import org.mogoldb.weberpBackend.entity.Usuario
import java.time.LocalDateTime

open interface NSEntity {
    open var codigo: Long;
    open var usuarioAtualizacao: Usuario?
    open var usuarioCriacao: Usuario?
    open var dataCriacao: LocalDateTime?
    open var dataModificacao: LocalDateTime?
}