package org.mongoldb.weberp.entity

import jakarta.persistence.*

@Entity
@Table(name = "cad_estado")
open class CadEstado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long? = null

    @Column(length = 60, unique = true, nullable = false, updatable = false)
    open var descricao: String? = null

    @Column(length = 2, unique = true, nullable = false, updatable = false)
    open var uf: String? = null

    @Column(unique = true, nullable = false, updatable = false)
    open var ibge: Long? = null
}