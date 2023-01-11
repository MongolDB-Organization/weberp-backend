package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.jwt.JwtTokenUtil
import org.mogoldb.weberpBackend.mail.EmailDetails
import org.mogoldb.weberpBackend.mail.EmailService
import org.mogoldb.weberpBackend.util.VerificationCodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AutenticacaoService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var autenticacaoService: UserDetailsService

    @Autowired
    private lateinit var usuarioService: UsuarioService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var emailService: EmailService


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

    fun signin(email: String, password: String): String {
        authenticateWithEmailAndPassword(email, password)
        val userDetails = autenticacaoService.loadUserByUsername(email) ?: throw NotFoundException()
        return jwtTokenUtil.generateToken(userDetails!!)
    }

    fun signup(nome: String, email: String, password: String, telefone: String?): String {
        val usuario = Usuario()
        usuario.nome = nome
        usuario.email = email
        usuario.senha = password
        usuario.telefone = telefone
        usuario.administrador = false
        usuarioService.save(usuario, null)
        authenticateWithEmailAndPassword(email, password)
        val userDetails = autenticacaoService.loadUserByUsername(email)
        return jwtTokenUtil.generateToken(userDetails!!)
    }

    @Throws(NotFoundException::class, BadRequestException::class)
    fun sendVerificationCode(email: String) {
        val verificationCode: String = VerificationCodeUtil.genVerifyCode()
        val usuario = usuarioService.findByEmail(email)
            ?: throw NotFoundException("Usuário com este email não foi encontrado")
        val emailDetails = EmailDetails(
            email!!,
            "Seu código de verificação é $verificationCode",
            "Código de verificação"
        )
        if (usuario!!.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (!emailService.sendMail(emailDetails)) {
            throw BadRequestException("Não foi possível enviar o email de verificação")
        }
        usuario.codigoVerificacao = verificationCode
        usuarioService.save(usuario)
    }

    @Throws(BadRequestException::class)
    fun validateVerificationCode(verifyCode: String) {
        val currentUser = usuarioService.getLoggedUser()
        if (currentUser!!.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (currentUser!!.codigoVerificacao != verifyCode) {
            throw BadRequestException("Código de verificação inválido")
        }
        currentUser.codigoVerificacao = null
        currentUser.verificado = true
        usuarioService.save(currentUser)
    }
}