package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*

@Entity
@Table(name = "estado_uf")
open class EstadoUf(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigo: Long,

    @Column(length = 60, unique = true, nullable = false, updatable = false)
    var descricao: String,

    @Column(length = 2, unique = true, nullable = false, updatable = false)
    var sigla: String,

    @Column(unique = true, nullable = false, updatable = false)
    var codigoIbge: Long,
)