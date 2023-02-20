package org.mongoldb.weberp.service.impl

import org.mongoldb.weberp.config.PasswordEncoderConfig
import org.mongoldb.weberp.entity.SisUsuario
import org.mongoldb.weberp.exception.BadRequestException
import org.mongoldb.weberp.exception.DuplicateEntryException
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.jwt.JwtTokenUtil
import org.mongoldb.weberp.mail.EmailDetails
import org.mongoldb.weberp.mail.EmailService
import org.mongoldb.weberp.repository.SisUsuarioRepository
import org.mongoldb.weberp.service.AutenticacaoService
import org.mongoldb.weberp.util.VerificationCodeUtil
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
    private lateinit var autenticacaoService: UserDetailsServiceImpl

    @Autowired
    private lateinit var sisUsuarioRepository: SisUsuarioRepository

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var emailService: EmailService

    @Autowired
    private lateinit var passwordEncoderConfiguration: PasswordEncoderConfig

    override fun getLoggedUser(): SisUsuario? {
        val email = SecurityContextHolder.getContext().authentication.name
        val userOptional = sisUsuarioRepository.findByEmail(email)
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
        val usuarioFindEmail = sisUsuarioRepository.findByEmail(email)
        if (usuarioFindEmail.isPresent) {
            throw DuplicateEntryException("email")
        }
        val sisUsuario = SisUsuario()
        sisUsuario.nome = nome
        sisUsuario.email = email
        sisUsuario.senha = passwordEncoderConfiguration.passwordEncoder()!!.encode(password)
        sisUsuario.telefone = telefone
        sisUsuario.administrador = false
        sisUsuarioRepository.save(sisUsuario)
        authenticateWithEmailAndPassword(email, password)
        val userDetails = autenticacaoService.loadUserByUsername(email)
        return jwtTokenUtil.generateToken(userDetails)
    }

    @Throws(NotFoundException::class, BadRequestException::class)
    override fun sendVerificationCode(email: String) {
        val verificationCode: String = VerificationCodeUtil.genVerifyCode()
        val usuario = sisUsuarioRepository.findByEmail(email).orElse(null) ?: throw NotFoundException("Usuário com este email não foi encontrado")
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
        sisUsuarioRepository.save(usuario)
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
        sisUsuarioRepository.save(currentUser)
    }
}