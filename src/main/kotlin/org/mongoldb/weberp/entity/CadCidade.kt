package org.mongoldb.weberp.entity

import jakarta.persistence.*

@Entity
@Table(name = "cad_cidade")
open class CadCidade(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long = 0L,

    @Column(nullable = false, updatable = false)
    open var descricao: String = "",

    @Column(unique = true, nullable = false, updatable = false)
    open var ibge: Long = 0L,

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    open var cadEstado: CadEstado = CadEstado(),
)