package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class AutenticacaoSendVerificationCodeDto(
    @get: NotNull
    @get: NotBlank
    @get: Email
    val email: String? = null
)