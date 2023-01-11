package org.mogoldb.weberpBackend.controller.v1.autenticacao

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.CadastroRequest
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.EntrarRequest
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.EnviarCodigoVerificacaoRequest
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.ValidarCodigoVerificacao
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.response.TokenResponse
import org.mogoldb.weberpBackend.payload.response.SuccessResponse
import org.mogoldb.weberpBackend.service.AutenticacaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@CrossOrigin
@RequestMapping("v1/autenticacao")
class AutenticacaoController {

    @Autowired
    lateinit var service: AutenticacaoService

    @PostMapping("/entrar")
    private fun signin(@Valid @RequestBody body: EntrarRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(TokenResponse(service.signin(body.email!!, body.senha!!)))
    }

    @PostMapping("/cadastrar")
    private fun signup(@Valid @RequestBody body: CadastroRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(TokenResponse(service.signup(body.nome!!, body.email!!, body.senha!!, body.telefone)))
    }

    @PostMapping("/enviar-codigo-verificao")
    @Throws(BadRequestException::class, NotFoundException::class)
    private fun sendVerificationCode(@Valid @RequestBody body: EnviarCodigoVerificacaoRequest): ResponseEntity<SuccessResponse> {
        service.sendVerificationCode(body.email!!)
        return ResponseEntity.ok().body(SuccessResponse("Código de verificação enviado"))
    }

    @PostMapping("/validar-codigo-verificao")
    @Throws(BadRequestException::class)
    private fun validateVerificationCode(@Valid @RequestBody body: ValidarCodigoVerificacao): ResponseEntity<SuccessResponse> {
        service.validateVerificationCode(body.codigo!!)
        return ResponseEntity.ok().body(SuccessResponse("Usuário verificado com sucesso"))
    }
}