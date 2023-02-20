package org.mongoldb.weberp.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "licenca")
open class Licenca(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0L,

    @Column(nullable = false)
    @get: NotNull
    open var dataInicio: LocalDateTime?,

    @Column(nullable = false)
    @get: NotNull
    open var dataVencimento: LocalDateTime?,

    @Column(nullable = false)
    @get: NotNull
    open var quantidadeDias: Int?,

    @Column(nullable = false)
    @get: NotNull
    open var quantidadeUsuarios: Int?,

    @Column(nullable = false)
    @get: NotNull
    open var quantidadeEmpresas: Int?,

    @ManyToOne
    open var sisContrato: SisContrato?,

    @OneToOne
    override var sisUsuarioAtualizacao: SisUsuario? = null,

    @OneToOne
    override var sisUsuarioCriacao: SisUsuario? = null,

    @Column(nullable = false, updatable = false) @CreationTimestamp
    override var dataCriacao: LocalDateTime?,

    @Column(nullable = false) @UpdateTimestamp
    override var dataModificacao: LocalDateTime?,
) : NSEntity
