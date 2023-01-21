package org.mogoldb.weberpBackend.service.impl

import org.mogoldb.weberpBackend.config.PasswordEncoderConfig
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.jwt.JwtTokenUtil
import org.mogoldb.weberpBackend.mail.EmailDetails
import org.mogoldb.weberpBackend.mail.EmailService
import org.mogoldb.weberpBackend.repository.UsuarioRepository
import org.mogoldb.weberpBackend.service.AutenticacaoService
import org.mogoldb.weberpBackend.service.UserDetailsService
import org.mogoldb.weberpBackend.util.VerificationCodeUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AutenticacaoServiceImpl : AutenticacaoService {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var autenticacaoService: UserDetailsService

    @Autowired
    private lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var passwordEncoderConfiguration: PasswordEncoderConfig

    override fun getLoggedUser(): Usuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val userOptional = usuarioRepository.findByEmail(email)
        return if (userOptional.isPresent) userOptional.get() else null
    }

    @Throws(BadRequestException::class)
    override fun authenticateWithEmailAndPassword(email: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        } catch (_: DisabledException) {
            throw BadRequestException("Usuário desabilitado")
        } catch (_: BadCredentialsException) {
            throw BadRequestException("Credenciais inválidas")
        }
    }

    override fun signin(email: String, password: String): String {
        authenticateWithEmailAndPassword(email, password)
        val userDetails = autenticacaoService.loadUserByUsername(email)
        return jwtTokenUtil.generateToken(userDetails)
    }

    override fun signup(nome: String, email: String, password: String, telefone: String?): String {
        val usuarioFindEmail = usuarioRepository.findByEmail(email)
        if (usuarioFindEmail.isPresent) {
            throw DuplicateEntryException("email")
        }
        val usuario = Usuario()
        usuario.nome = nome
        usuario.email = email
        usuario.senha = passwordEncoderConfiguration.passwordEncoder()!!.encode(password)
        usuario.telefone = telefone
        usuario.administrador = false
        usuarioRepository.save(usuario)
        authenticateWithEmailAndPassword(email, password)
        val userDetails = autenticacaoService.loadUserByUsername(email)
        return jwtTokenUtil.generateToken(userDetails)
    }

    @Throws(NotFoundException::class, BadRequestException::class)
    override fun sendVerificationCode(email: String) {
        val verificationCode: String = VerificationCodeUtil.genVerifyCode()
        val usuario = usuarioRepository.findByEmail(email).orElse(null) ?: throw NotFoundException("Usuário com este email não foi encontrado")
        val emailDetails = EmailDetails(
            email, "Seu código de verificação é $verificationCode", "Código de verificação"
        )
        if (usuario.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (!emailService.sendMail(emailDetails)) {
            throw BadRequestException("Não foi possível enviar o email de verificação")
        }
        usuario.codigoVerificacao = verificationCode
        usuarioRepository.save(usuario)
    }

    @Throws(BadRequestException::class)
    override fun validateVerificationCode(verifyCode: String) {
        val currentUser = getLoggedUser()
        if (currentUser!!.verificado) {
            throw BadRequestException("Usuário já verificado")
        }
        if (currentUser.codigoVerificacao != verifyCode) {
            throw BadRequestException("Código de verificação inválido")
        }
        currentUser.codigoVerificacao = null
        currentUser.verificado = true
        usuarioRepository.save(currentUser)
    }
}