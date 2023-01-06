package org.mogoldb.weberpBackend.middleware

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class AuthEntryPoint : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?
    ) {
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write("Não autorizado")
    }
}