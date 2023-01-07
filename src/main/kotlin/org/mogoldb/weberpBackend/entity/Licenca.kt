package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.DefaultEntity
import java.time.LocalDateTime

@Entity
@Table(name = "licenca")
open class Licenca(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    var dataInicio: LocalDateTime?,

    @Column(nullable = false)
    @get: NotNull
    var dataVencimento: LocalDateTime?,

    @Column(nullable = false)
    @get: NotNull
    var quantidadeDias: Int?,

    @Column(nullable = false)
    @get: NotNull
    var limiteUsuario: Int?,

    @OneToOne(mappedBy = "licenca", cascade = [CascadeType.ALL])
    val empresa: Empresa?,

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