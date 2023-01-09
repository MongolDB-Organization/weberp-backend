package org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class EnviarCodigoVerificacaoRequest(@get: NotNull @get: NotBlank @get: Email val email: String? = null)