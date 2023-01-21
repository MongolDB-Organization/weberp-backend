package org.mogoldb.weberpBackend.handler

import jakarta.validation.ConstraintViolationException
import org.mogoldb.weberpBackend.dto.response.DuplicatedEntryErrorResponseDTO
import org.mogoldb.weberpBackend.dto.response.ErrorResponseDTO
import org.mogoldb.weberpBackend.dto.response.MultiErrorResponseDTO
import org.mogoldb.weberpBackend.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer

@RestControllerAdvice
class ControllerExceptionHandler {

    companion object {
        const val NO_MESSAGE_AVAILABLE = "Nenhuma mensagem disponível"
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponseDTO> {
        val status = HttpStatus.NOT_FOUND.value()
        val errorMessage = ErrorResponseDTO(status, "Not Found", ex.message ?: NO_MESSAGE_AVAILABLE)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ErrorResponseDTO> {
        val status = HttpStatus.BAD_REQUEST.value()
        val errorMessage = ErrorResponseDTO(status, "Bad Request", ex.message)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler(NotAcceptableException::class)
    fun handleNotAcceptableException(ex: NotAcceptableException): ResponseEntity<ErrorResponseDTO> {
        val status = HttpStatus.NOT_ACCEPTABLE.value()
        val errorMessage = ErrorResponseDTO(status, "Not Acceptable", ex.message)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<ErrorResponseDTO> {
        val status = HttpStatus.NOT_ACCEPTABLE.value()
        val errorMessage = ErrorResponseDTO(status, "Not Acceptable", ex.message as String)
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler(DuplicateEntryException::class)
    fun handleDuplicateEntryException(ex: DuplicateEntryException): ResponseEntity<DuplicatedEntryErrorResponseDTO> {
        val status = HttpStatus.BAD_REQUEST.value()
        val errorMessage = DuplicatedEntryErrorResponseDTO(
            status, "Entrada duplicada", ex.field, "O valor deste campo não pode estar duplicado no banco de dados"
        )
        return ResponseEntity.status(status).body(errorMessage)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): ResponseEntity<MultiErrorResponseDTO> {
        val errors: MutableMap<String, String?> = HashMap()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage
        })
        val status = HttpStatus.BAD_REQUEST.value()
        val multiErrorResponse = MultiErrorResponseDTO(status, errors, "Validation Error")
        return ResponseEntity.status(status).body(multiErrorResponse)
    }

    @ExceptionHandler(NoPermitionException::class)
    fun handleNoPermitionException(ex: NoPermitionException): ResponseEntity<ErrorResponseDTO> {
        val status = HttpStatus.BAD_REQUEST.value()
        val errorMessage = ErrorResponseDTO(
            status, "Sem permissão", ex.message ?: NO_MESSAGE_AVAILABLE,
        )
        return ResponseEntity.status(status).body(errorMessage)
    }
}
