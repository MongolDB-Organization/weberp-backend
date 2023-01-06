package org.mogoldb.weberpBackend.payload.response

import java.time.LocalDateTime

open class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
