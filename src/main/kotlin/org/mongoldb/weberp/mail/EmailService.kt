package org.mongoldb.weberp.mail

import org.mongoldb.weberp.mail.EmailDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.io.File

@Service
class EmailService(
    @Autowired private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val mailSender: String,
) {
    fun sendMail(emailDetails : EmailDetails) : Boolean {
        val mailMessage = SimpleMailMessage()
        return try {
            mailMessage.from = mailSender
            mailMessage.text = emailDetails.msgBody
            mailMessage.subject = emailDetails.subject
            mailMessage.setTo(emailDetails.recipient)
            javaMailSender.send(mailMessage)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun sendMailWithAttachment(emailDetails : EmailDetails) : Boolean {
        val mimeMessage = javaMailSender.createMimeMessage()
        return try {
            val mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom(mailSender)
            mimeMessageHelper.setTo(emailDetails.recipient)
            mimeMessageHelper.setText(emailDetails.msgBody)
            mimeMessageHelper.setSubject(emailDetails.subject)
            val file = FileSystemResource(File(emailDetails.attachment))
            mimeMessageHelper.addAttachment(file.filename, file)
            javaMailSender.send(mimeMessage)
            return true
        } catch (_: Exception) {
            false
        }
    }
}