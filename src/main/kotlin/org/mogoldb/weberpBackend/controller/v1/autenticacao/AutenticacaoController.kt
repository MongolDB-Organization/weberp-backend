package org.mogoldb.weberpBackend.controller.v1.autenticacao

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.payload.request.LoginRequest
import org.mogoldb.weberpBackend.payload.response.LoginResponse
import org.mogoldb.weberpBackend.service.UserDetailsService
import org.mogoldb.weberpBackend.util.JwtTokenUtil
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
    @Autowired
    private val authenticationManager: AuthenticationManager,
    @Autowired
    private val jwtTokenUtil: JwtTokenUtil,
    @Autowired
    private val autenticacaoService: UserDetailsService,
) {

    @Throws(BadRequestException::class)
    private fun authenticate(email: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        } catch (_: DisabledException) {
            throw BadRequestException("Usuário desabilitado")
        } catch (_: BadCredentialsException) {
            throw BadRequestException("Credenciais inválidas")
        }
    }

    @PostMapping("/entrar")
    fun createAuthenticationToken(@Valid @RequestBody body: LoginRequest): ResponseEntity<LoginResponse> {
        authenticate(body.email!!, body.senha!!)
        val userDetails = autenticacaoService.loadUserByUsername(body.email)
            ?: throw NotFoundException()

        val token = jwtTokenUtil.generateToken(userDetails!!)
        return ResponseEntity.ok(LoginResponse(token))
    }
}