package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "cidade_uf")
open class CidadeUf(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long = 0,

    @Column(unique = true, nullable = false, updatable = false)
    open var codigoIbge: Long = 0,

    @Column(nullable = false, updatable = false)
    open var descricao: String = "",

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    open var estadoUf: EstadoUf,
)