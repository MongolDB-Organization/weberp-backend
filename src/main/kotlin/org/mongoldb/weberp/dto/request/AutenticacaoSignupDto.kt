package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AutenticacaoSignupDto(
    @get: NotNull
    @get: NotBlank
    var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    @get: Email
    var email: String? = null,

    @get: NotNull
    @get: NotBlank
    var senha: String? = null,

    var telefone: String? = null
)
