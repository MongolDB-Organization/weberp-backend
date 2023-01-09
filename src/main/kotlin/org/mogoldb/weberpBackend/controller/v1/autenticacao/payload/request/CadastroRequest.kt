package org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

open class CadastroRequest(
    @get: NotNull
    @get: NotBlank
    open var nome: String? = null,

    @get: NotNull
    @get: NotBlank
    open var email: String? = null,

    @get: NotNull
    @get: NotBlank
    open var senha: String? = null,

    open var telefone: String? = null
)