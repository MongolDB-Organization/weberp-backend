package org.mogoldb.weberpBackend.middleware

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class AuthEntryPoint : AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(AuthEntryPoint::class.java)

    @Throws(IOException::class, ServletException::class)
    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?
    ) {
        logger.error("Unauthorized error: {}", authException!!.message);
        response!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}