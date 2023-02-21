package org.mongoldb.weberp.entity

import jakarta.persistence.*

@Entity
@Table(name = "cad_cidade")
open class CadCidade {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(updatable = false)
    open var codigo: Long? = null

    @Column(nullable = false, updatable = false)
    open var descricao: String? = null

    @Column(unique = true, nullable = false, updatable = false)
    open var ibge: Long? = null

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    open var cadEstado: CadEstado? = null
}
