package org.mogoldb.weberpBackend.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
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
    var nome: String? = null,

    @Column(unique = true, nullable = false)
    @get: Email
    @get: NotNull
    @get: NotBlank
    var email: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    var senha: String? = null,

    var telefone: String? = null,

    @OneToOne
    @JsonBackReference
    override var usuarioAtualizacao: Usuario? = null,

    @OneToOne
    @JsonBackReference
    override var usuarioCriacao: Usuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : DefaultEntity