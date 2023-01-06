package org.mogoldb.weberpBackend.payload.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class LoginRequest (
    @get: NotNull
    @get: NotBlank
    @get: Email
    val email: String?,

    @get: NotNull
    @get: NotBlank
    val senha: String?,
)