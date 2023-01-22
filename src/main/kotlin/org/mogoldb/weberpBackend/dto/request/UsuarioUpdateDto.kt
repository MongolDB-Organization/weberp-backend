package org.mogoldb.weberpBackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mogoldb.weberpBackend.entity.Usuario

data class UsuarioUpdateDto(
    @get: NotBlank
    var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    var telefone: String? = null,
) {

    fun toEntity(mergeEntity: Usuario?): Usuario {
        val entity = mergeEntity ?: Usuario()
        entity.nome = nome
        entity.email = email
        entity.telefone = telefone
        return entity
    }
}