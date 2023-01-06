package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import java.time.LocalDateTime

@Entity
@Table(name = "empresa")
open class Empresa(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var razaoSocial: String?,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var nomeFantasia: String?,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    var incricaoEstadual: String?,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    var cnpj: String?,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var email: String?,

    @OneToOne
    @get: NotNull
    val modificadoPor: Usuario?,

    @OneToOne
    @get: NotNull
    val criadoPor: Usuario?,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var telefone: String?,

    @Column(nullable = false)
    @CreationTimestamp
    var dataCriacao: LocalDateTime?,

    @Column(nullable = false)
    @UpdateTimestamp
    var dataModificacao: LocalDateTime?,
) : DefaultEntity