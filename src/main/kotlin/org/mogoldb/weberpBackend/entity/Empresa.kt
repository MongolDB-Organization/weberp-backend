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
    var razaoSocial: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var nomeFantasia: String? = null,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    var incricaoEstadual: String? = null,

    @Column(nullable = false, unique = true)
    @get: NotNull
    @get: NotBlank
    var cnpj: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    @Column(nullable = false)
    @get:NotNull
    @get:NotBlank
    var telefone: String? = null,

    @OneToOne
    @JoinColumn(name = "licenca_codigo", nullable = true)
    var licenca: Licenca? = null,

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