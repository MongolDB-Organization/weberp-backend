package org.mongoldb.weberp.entity

import jakarta.persistence.*

@Entity
@Table(name = "cad_estado")
open class CadEstado(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long = 0L,

    @Column(length = 60, unique = true, nullable = false, updatable = false)
    open var descricao: String = "",

    @Column(length = 2, unique = true, nullable = false, updatable = false)
    open var uf: String = "",

    @Column(unique = true, nullable = false, updatable = false)
    open var ibge: Long = 0L,
)