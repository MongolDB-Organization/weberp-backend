package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSContratoLevelEntity
import java.time.LocalDateTime

@Entity
@Table(name = "cad_empresa")
open class CadEmpresa(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0L,

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "sis_contrato_codigo")
    override var sisContrato: SisContrato? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var sisUsuarioAtualizacao: SisUsuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var sisUsuarioCriacao: SisUsuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = LocalDateTime.now(),

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = LocalDateTime.now(),
) : NSContratoLevelEntity {

    @OneToMany(mappedBy = "cadEmpresa", cascade = [CascadeType.ALL], orphanRemoval = true, targetEntity = CadEmpresaEndereco::class)
    open var cadEmpresaEnderecos: MutableList<CadEmpresaEndereco> = mutableListOf()
}
