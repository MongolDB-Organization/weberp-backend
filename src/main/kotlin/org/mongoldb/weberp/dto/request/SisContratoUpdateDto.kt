package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.SisContrato

open class SisContratoUpdateDto (
    @get: NotNull
    @get: NotBlank
    var nome: String? = null
) {
    open fun toEntity(mergeEntity: SisContrato?): SisContrato {
        val entity = mergeEntity ?: SisContrato()
        entity.nome = nome ?: ""
        return entity
    }
}