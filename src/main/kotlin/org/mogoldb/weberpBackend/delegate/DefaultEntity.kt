package org.mogoldb.weberpBackend.delegate

import org.mogoldb.weberpBackend.entity.Usuario
import java.time.LocalDateTime

open interface DefaultEntity {
    open var codigo: Long;
    open var usuarioAtualizacao: Usuario?
    open var usuarioCriacao: Usuario?
    open var dataCriacao: LocalDateTime?
    open var dataModificacao: LocalDateTime?
}