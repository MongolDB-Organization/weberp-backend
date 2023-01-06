package org.mogoldb.weberpBackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.NotBlank
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import java.time.LocalDateTime

@Entity
@Table(name = "usuario")
open class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var nome: String?,

    @Column(unique = true, nullable = false)
    @get: Email
    @get: NotNull
    @get: NotBlank
    var email: String?,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var senha: String?,

    var telefone: String?,

    @Column(nullable = false)
    @CreationTimestamp
    var dataCriacao: LocalDateTime?,

    @Column(nullable = false)
    @UpdateTimestamp
    var dataModificacao: LocalDateTime?,
) : DefaultEntity