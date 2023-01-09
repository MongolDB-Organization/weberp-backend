package org.mogoldb.weberpBackend.controller.v1.autenticacao

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.CadastroRequest
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.EntrarRequest
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.EnviarCodigoVerificacaoRequest
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.request.ValidarCodigoVerificacao
import org.mogoldb.weberpBackend.controller.v1.autenticacao.payload.response.TokenResponse
import org.mogoldb.weberpBackend.mail.EmailDetails
import org.mogoldb.weberpBackend.mail.EmailService
import org.mogoldb.weberpBackend.payload.response.SuccessResponse
import org.mogoldb.weberpBackend.service.UserDetailsService
import org.mogoldb.weberpBackend.service.UsuarioService
import org.mogoldb.weberpBackend.util.JwtTokenUtil
import org.mogoldb.weberpBackend.util.VerificationCodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@CrossOrigin
@RequestMapping("v1/autenticacao")
class AutenticacaoController(
    @Autowired private val authenticationManager: AuthenticationManager,
    @Autowired private val jwtTokenUtil: JwtTokenUtil,
    @Autowired private val autenticacaoService: UserDetailsService,
    @Autowired private val usuarioService: UsuarioService,
    @Autowired private val emailService: EmailService,
) {

    @Throws(BadRequestException::class)
    private fun authenticateWithEmailAndPassword(email: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        } catch (_: DisabledException) {
            throw BadRequestException("Usuário desabilitado")
        } catch (_: BadCredentialsException) {
            throw BadRequestException("Credenciais inválidas")
        }
    }

    @PostMapping("/entrar")
    private fun signin(@Valid @RequestBody body: EntrarRequest): ResponseEntity<TokenResponse> {
        authenticateWithEmailAndPassword(body.email!!, body.senha!!)
        val userDetails = autenticacaoService.loadUserByUsername(body.email) ?: throw NotFoundException()
        val token = jwtTokenUtil.generateToken(userDetails!!)
        val emailDetails = EmailDetails(body.email!!, "Foi realizado um login em seu usuário", "Login realizado")
        if (!emailService.sendMail(emailDetails))
            throw BadRequestException("Nãoi foi possível enviar o email")
        return ResponseEntity.ok(TokenResponse(token))
    }


    @PostMapping("/cadastrar")
    private fun signup(@Valid @RequestBody body: CadastroRequest): ResponseEntity<TokenResponse> {
        val usuario = Usuario()
        usuario.nome = body.nome
        usuario.email = body.email
        usuario.senha = body.senha
        usuario.administrador = false
        usuario.telefone = body.telefone
        val email = usuario.email ?: ""
        val senha = usuario.senha ?: ""
        usuarioService.save(usuario, null)
        authenticateWithEmailAndPassword(email, senha)
        val userDetails = autenticacaoService.loadUserByUsername(body.email) ?: throw NotFoundException()
        val token = jwtTokenUtil.generateToken(userDetails!!)
        return ResponseEntity.ok().body(TokenResponse(token))
    }

    @PostMapping("/enviar-codigo-verificao")
    private fun sendVerificationCode(@Valid @RequestBody body: EnviarCodigoVerificacaoRequest) : ResponseEntity<SuccessResponse> {
        val verificationCode: String = VerificationCodeUtil.genVerifyCode()
        val usuario = usuarioService.findByEmail(body.email!!) ?: throw NotFoundException("Usuário com este email não foi encontrado")
        val emailDetails = EmailDetails(
            body.email!!,
            "Seu código de verificação é $verificationCode",
            "Código de verificação")
        if (usuario!!.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (!emailService.sendMail(emailDetails)) {
            throw BadRequestException("Não foi possível enviar o email de verificação")
        }
        usuario.codigoVerificacao = verificationCode
        usuarioService.save(usuario)
        return ResponseEntity.ok().body(SuccessResponse("Código de verificação enviado"))
    }

    @PostMapping("/validar-codigo-verificao")
    private fun validateVerificationCode(@Valid @RequestBody body: ValidarCodigoVerificacao) : ResponseEntity<SuccessResponse> {
        val currentUser = usuarioService.getLoggedUser()
        if (currentUser!!.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (currentUser!!.codigoVerificacao == body.codigo) {
            currentUser.codigoVerificacao = null
            currentUser.verificado = true
            usuarioService.save(currentUser)
            return ResponseEntity.ok().body(SuccessResponse("Usuário verificado com sucesso"))
        } else {
            throw BadRequestException("Código de verificação inválido")
        }
    }
}