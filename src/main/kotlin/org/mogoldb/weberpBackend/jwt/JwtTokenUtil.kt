package org.mogoldb.weberpBackend.jwt;

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component;
import java.util.Date
import java.util.HashMap
import java.util.function.Function

@Component
class JwtTokenUtil(@Value("\${jwt.secret}") private val secret: String) {

    companion object {
        const val JWT_TOKEN_VALIDITY: Long = 5 * 60 * 60
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts
            .parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token, Claims::getSubject);
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    fun isTokenExpired(token: String): Boolean {
        return getExpirationDateFromToken(token).before(Date())
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, Any>()
        return doGenerateToken(claims, userDetails.username!!)
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username!! && !isTokenExpired(token)
    }
}