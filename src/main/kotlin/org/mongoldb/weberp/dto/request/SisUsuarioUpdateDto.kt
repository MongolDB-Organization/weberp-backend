package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.SisUsuario

data class SisUsuarioUpdateDto(
    @get: NotBlank
    var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    var telefone: String? = null,
) {

    fun toEntity(mergeEntity: SisUsuario?): SisUsuario {
        val entity = mergeEntity ?: SisUsuario()
        entity.nome = nome
        entity.email = email
        entity.telefone = telefone
        return entity
    }
}