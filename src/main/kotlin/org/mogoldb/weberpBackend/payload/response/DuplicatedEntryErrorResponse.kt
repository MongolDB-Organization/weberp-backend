package org.mogoldb.weberpBackend.payload.response

class DuplicatedEntryErrorResponse(status: Int, error: String, val field: String, message: String) :
    ErrorResponse(status, error, message)