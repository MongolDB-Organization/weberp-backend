package org.mogoldb.weberpBackend.payload.response

import java.time.LocalDateTime

open class MultiErrorResponse(
    val status: Int,
    val errors: MutableMap<String, String?>,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
