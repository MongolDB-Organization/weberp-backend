package org.mogoldb.weberpBackend.handler

import jakarta.validation.ConstraintViolationException
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.DuplicateEntryException
import org.mogoldb.weberpBackend.exception.NotAcceptableException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.payload.response.DuplicatedEntryErrorResponse
import org.mogoldb.weberpBackend.payload.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_FOUND.value()
        val errorMessage = ErrorResponse(status, "Not Found", "No message available")
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.BAD_REQUEST.value()
        val errorMessage = ErrorResponse(status, "Bad Request", ex.message)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler
    fun handleNotAcceptableException(ex: NotAcceptableException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_ACCEPTABLE.value()
        val errorMessage = ErrorResponse(status, "Not Acceptable", ex.message)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        val status = HttpStatus.NOT_ACCEPTABLE.value()
        val errorMessage = ErrorResponse(status, "Not Acceptable", ex.message as String)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler
    fun handleDuplicateEntryException(ex: DuplicateEntryException): ResponseEntity<DuplicatedEntryErrorResponse> {
        val status = HttpStatus.BAD_REQUEST.value()
        val errorMessage = DuplicatedEntryErrorResponse(
            status,
            "Duplicate Entry",
            ex.field,
            "The value of this field cannot be duplicated in the database")
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): Map<String, String?>? {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        return errors
    }
}
