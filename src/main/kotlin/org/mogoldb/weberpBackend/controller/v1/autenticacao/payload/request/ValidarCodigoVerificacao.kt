package org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

class ValidarCodigoVerificacao (@get: NotBlank @get: NotNull val codigo: String? = null)