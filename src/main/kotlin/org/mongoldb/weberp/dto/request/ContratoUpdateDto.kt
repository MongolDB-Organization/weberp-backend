package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.Contrato

open class ContratoUpdateDto (
    @get: NotNull
    @get: NotBlank
    var nome: String? = null
) {
    open fun toEntity(mergeEntity: Contrato?): Contrato {
        val entity = mergeEntity ?: Contrato()
        entity.nome = nome ?: ""
        return entity
    }
}