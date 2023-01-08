package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import java.time.LocalDateTime

@Entity
@Table(name = "contrato")
open class Contrato(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    open var nome: String,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contrato_codigo")
    open var empresas: ArrayList<Empresa> = arrayListOf<Empresa>(),

    @OneToOne
    open var licenca: Licenca?,

    @OneToOne
    override var usuarioCriacao: Usuario?,

    @OneToOne
    override var usuarioAtualizacao: Usuario?,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime?,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime?,
) : DefaultEntity