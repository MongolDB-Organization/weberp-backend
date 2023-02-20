package org.mongoldb.weberp.dto.response

import java.time.LocalDateTime

open class MultiErrorResponseDTO(
    val status: Int,
    val errors: MutableMap<String, String?>,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
