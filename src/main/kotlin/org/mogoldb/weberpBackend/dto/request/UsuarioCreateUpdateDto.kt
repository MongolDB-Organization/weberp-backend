package org.mogoldb.weberpBackend.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class UsuarioCreateUpdateDto (
    @get: NotBlank
    open var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    open var email: String? = null,

    open var telefone: String? = null,
)