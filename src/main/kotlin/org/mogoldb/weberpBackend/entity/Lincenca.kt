package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import org.mogoldb.weberpBackend.delegate.DefaultEntity

@Entity
@Table(name = "lincenca")
open class Lincenca(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,
) : DefaultEntity