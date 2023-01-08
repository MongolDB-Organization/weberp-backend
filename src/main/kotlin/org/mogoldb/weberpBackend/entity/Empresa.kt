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
    open var razaoSocial: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    open var nomeFantasia: String? = null,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    open var incricaoEstadual: String? = null,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    open var cnpj: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    open var email: String? = null,

    @Column(nullable = false)
    @get:NotNull
    @get:NotBlank
    open var telefone: String? = null,

    @ManyToOne
    open var contrato: Contrato?,

    @OneToOne
    override var usuarioAtualizacao: Usuario?,

    @OneToOne
    override var usuarioCriacao: Usuario?,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime?,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime?,
) : DefaultEntity