package org.mogoldb.weberpBackend.controller.v1

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.controller.v1.autenticacao.dto.*
import org.mogoldb.weberpBackend.dto.request.AutenticacaoEntrarDto
import org.mogoldb.weberpBackend.dto.request.AutenticacaoSendVerificationCodeDto
import org.mogoldb.weberpBackend.dto.request.AutenticacaoSignupDto
import org.mogoldb.weberpBackend.dto.request.AutenticacoValidateVerificationCodeDto
import org.mogoldb.weberpBackend.dto.response.AutenticaocaTokenDto
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.dto.response.SuccessResponseDTO
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
    private fun signin(@Valid @RequestBody body: AutenticacaoEntrarDto): ResponseEntity<AutenticaocaTokenDto> {
        return ResponseEntity.ok(AutenticaocaTokenDto(service.signin(body.email!!, body.senha!!)))
    }

    @PostMapping("/cadastrar")
    private fun signup(@Valid @RequestBody body: AutenticacaoSignupDto): ResponseEntity<AutenticaocaTokenDto> {
        return ResponseEntity.ok(AutenticaocaTokenDto(service.signup(body.nome!!, body.email!!, body.senha!!, body.telefone)))
    }

    @PostMapping("/enviar-codigo-verificao")
    @Throws(BadRequestException::class, NotFoundException::class)
    private fun sendVerificationCode(@Valid @RequestBody body: AutenticacaoSendVerificationCodeDto): ResponseEntity<SuccessResponseDTO> {
        service.sendVerificationCode(body.email!!)
        return ResponseEntity.ok().body(SuccessResponseDTO("Código de verificação enviado"))
    }

    @PostMapping("/validar-codigo-verificao")
    @Throws(BadRequestException::class)
    private fun validateVerificationCode(@Valid @RequestBody body: AutenticacoValidateVerificationCodeDto): ResponseEntity<SuccessResponseDTO> {
        service.validateVerificationCode(body.codigo!!)
        return ResponseEntity.ok().body(SuccessResponseDTO("Usuário verificado com sucesso"))
    }
}