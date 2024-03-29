package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.entity.NSContratoLevelEntity
import java.time.LocalDateTime

@Entity
@Table(name = "empresa")
open class Empresa(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    open var razaoSocial: String = "",

    @Column(nullable = false)
    open var nomeFantasia: String = "",

    @Column(nullable = false, unique = true)
    open var incricaoEstadual: String = "",

    @Column(nullable = false, unique = true)
    open var cnpj: String = "",

    @Column(nullable = false)
    open var email: String = "",

    @Column(nullable = false)
    open var telefone: String = "",

    @ManyToOne(cascade = [CascadeType.REMOVE])
    @JoinColumn(nullable = false)
    override var contrato: Contrato? = null,

    @OneToOne
    override var usuarioAtualizacao: Usuario? = null,

    @OneToOne
    override var usuarioCriacao: Usuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = LocalDateTime.now(),
) : NSContratoLevelEntity