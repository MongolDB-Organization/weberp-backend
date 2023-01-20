package org.mogoldb.weberpBackend.dto.response

class DuplicatedEntryErrorResponseDTO(status: Int, error: String, val field: String, message: String) :
    ErrorResponseDTO(status, error, message)