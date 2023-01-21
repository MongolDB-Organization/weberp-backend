package org.mogoldb.weberpBackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UsuarioCreateUpdateDto (
    @get: NotBlank
    var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    var telefone: String? = null,
)