package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "sis_contrato")
data class SisContrato(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false, unique = true)
    var nome: String = "",

    @OneToMany(targetEntity = CadEmpresa::class, fetch = FetchType.LAZY, mappedBy = "sisContrato")
    var cadEmpresas: MutableList<CadEmpresa> = arrayListOf<CadEmpresa>(),

    @OneToOne
    @JoinColumn(name = "sis_licenca_codigo")
    var sisLicenca: SisLicenca? = null,

    @ManyToMany(targetEntity = SisUsuario::class, fetch = FetchType.LAZY)
    @JoinTable(name = "sis_contrato_usuario", joinColumns = [JoinColumn(name = "sis_contrato_codigo")], inverseJoinColumns = [JoinColumn(name = "sis_usuario_codigo")])
    var sisUsuarios: MutableList<SisUsuario> = arrayListOf<SisUsuario>(),


    @OneToOne(fetch = FetchType.LAZY)
    var sisUsuarioProprietario: SisUsuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var sisUsuarioCriacao: SisUsuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var sisUsuarioAtualizacao: SisUsuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : NSEntity
