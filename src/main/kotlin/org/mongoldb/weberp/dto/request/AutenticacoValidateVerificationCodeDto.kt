package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AutenticacoValidateVerificationCodeDto(
    @get: NotBlank
    @get: NotNull
    val codigo: String? = null
)