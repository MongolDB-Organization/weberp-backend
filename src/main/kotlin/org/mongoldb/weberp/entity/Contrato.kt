package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "contrato")
data class Contrato(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    var nome: String = "",

    @OneToMany(cascade = [CascadeType.ALL], targetEntity = Empresa::class)
    @JoinColumn(name = "contrato_codigo")
    var empresas: Set<Empresa> = HashSet<Empresa>(),

    @OneToOne var licenca: Licenca? = null,
    @ManyToMany(cascade = [CascadeType.PERSIST], targetEntity = CadUsuario::class, mappedBy = "contratos")
    var cadUsuarios: MutableList<CadUsuario> = arrayListOf<CadUsuario>(),

    @OneToOne()
    var cadUsuarioProprietario: CadUsuario? = null,

    @OneToOne()
    override var cadUsuarioCriacao: CadUsuario? = null,

    @OneToOne()
    override var cadUsuarioAtualizacao: CadUsuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : NSEntity