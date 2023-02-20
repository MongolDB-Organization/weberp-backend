package org.mongoldb.weberp.dto.response

import java.time.LocalDateTime

open class ErrorResponseDTO(
    val status: Int,
    val error: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
