package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AutenticacaoEntrarDto(
    @get: NotNull
    @get: NotBlank
    @get: Email
    val email: String?,

    @get: NotNull
    @get: NotBlank
    val senha: String?,
)