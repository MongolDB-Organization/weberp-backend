package org.mogoldb.weberpBackend.dto.response

data class AutenticaocaTokenDto(
    val token: String,
    val logged: Boolean = true
)