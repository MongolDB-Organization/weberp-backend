package org.mongoldb.weberp.dto.response

data class AutenticaocaTokenDto(
    val token: String,
    val logged: Boolean = true
)