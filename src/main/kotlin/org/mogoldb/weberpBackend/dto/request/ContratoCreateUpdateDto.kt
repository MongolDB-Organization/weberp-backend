package org.mogoldb.weberpBackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ContratoCreateUpdateDto (
    @get: NotNull
    @get: NotBlank
    var nome: String? = null
)