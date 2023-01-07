package org.mogoldb.weberpBackend.delegate

import org.mogoldb.weberpBackend.entity.Usuario
import java.time.LocalDateTime

interface DefaultEntity {
    var codigo: Long;
    var usuarioAtualizacao: Usuario?
    var usuarioCriacao: Usuario?
    var dataCriacao: LocalDateTime?
    var dataModificacao: LocalDateTime?
}