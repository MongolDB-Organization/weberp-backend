package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.CadUsuario

data class CadUsuarioUpdateDto(
    @get: NotBlank
    var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    var telefone: String? = null,
) {

    fun toEntity(mergeEntity: CadUsuario?): CadUsuario {
        val entity = mergeEntity ?: CadUsuario()
        entity.nome = nome
        entity.email = email
        entity.telefone = telefone
        return entity
    }
}