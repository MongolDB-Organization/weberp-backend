package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "contrato")
@NamedEntityGraph(name = "Contrato.clean",
    attributeNodes = [
        NamedAttributeNode("empresas"),
        NamedAttributeNode("licenca"),
        NamedAttributeNode("usuarios"),
        NamedAttributeNode("usuarioCriacao"),
        NamedAttributeNode("usuarioProprietario"),
        NamedAttributeNode("usuarioAtualizacao"),
    ])
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
    @ManyToMany(cascade = [CascadeType.PERSIST], targetEntity = Usuario::class, mappedBy = "contratos")
    var usuarios: MutableList<Usuario> = arrayListOf<Usuario>(),

    @OneToOne()
    var usuarioProprietario: Usuario? = null,

    @OneToOne()
    override var usuarioCriacao: Usuario? = null,

    @OneToOne()
    override var usuarioAtualizacao: Usuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : NSEntity