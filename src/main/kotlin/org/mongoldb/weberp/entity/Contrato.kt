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
    @ManyToMany(cascade = [CascadeType.PERSIST], targetEntity = SisUsuario::class, mappedBy = "contratos")
    var sisUsuarios: MutableList<SisUsuario> = arrayListOf<SisUsuario>(),

    @OneToOne()
    var sisUsuarioProprietario: SisUsuario? = null,

    @OneToOne()
    override var sisUsuarioCriacao: SisUsuario? = null,

    @OneToOne()
    override var sisUsuarioAtualizacao: SisUsuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : NSEntity