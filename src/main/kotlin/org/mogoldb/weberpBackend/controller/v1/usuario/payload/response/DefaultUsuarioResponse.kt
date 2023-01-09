package org.mogoldb.weberpBackend.controller.v1.usuario.payload.response

import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.entity.Usuario

class DefaultUsuarioResponse(
    val nome: String,
    val email: String,
    val telefone: String,
    val adiministrador: Boolean,
    val contratos: List<Contrato>,
    val empresas: List<Empresa>,
) {
    companion object {
        fun fromUsuario(usuario: Usuario): DefaultUsuarioResponse {
            return DefaultUsuarioResponse(
                usuario.nome!!,
                usuario.email!!,
                usuario.telefone!!,
                usuario.administrador,
                usuario.contratos,
                usuario.empresas,
            )
        }
    }
}