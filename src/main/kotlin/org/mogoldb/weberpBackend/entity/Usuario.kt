package org.mogoldb.weberpBackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import java.time.LocalDateTime
import java.util.ArrayList
import java.util.Collections

@Entity
@Table(name = "usuario")
open class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    open var nome: String? = null,

    @Column(unique = true, nullable = false)
    @get: Email
    @get: NotNull
    @get: NotBlank
    open var email: String? = null,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    open var senha: String? = null,

    open var telefone: String? = null,

    @Column(nullable = false)
    open var administrador: Boolean = false,

    @ManyToMany(targetEntity = Contrato::class)
    open var contratos: List<Contrato> = ArrayList<Contrato>(),

    @ManyToMany(targetEntity = Empresa::class)
    open var empresas: List<Empresa> = ArrayList<Empresa>(),

    @OneToOne
    @JsonIgnore
    override var usuarioAtualizacao: Usuario? = null,

    @OneToOne
    @JsonIgnore
    override var usuarioCriacao: Usuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : DefaultEntity {
}