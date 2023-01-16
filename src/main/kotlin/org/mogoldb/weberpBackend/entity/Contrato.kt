package org.mogoldb.weberpBackend.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mogoldb.weberpBackend.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "contrato")
data class Contrato(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0,

    @Column(nullable = false)
    @get: NotNull
    @get: NotBlank
    open var nome: String? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], targetEntity = Empresa::class)
    @JoinColumn(name = "contrato_codigo")
    open var empresas: Set<Empresa> = HashSet<Empresa>(),

    @OneToOne open var licenca: Licenca? = null,
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Usuario::class, mappedBy = "contratos")
    open var usuarios: MutableList<Usuario> = arrayListOf<Usuario>(),

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    open var usuarioProprietario: Usuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var usuarioCriacao: Usuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    override var usuarioAtualizacao: Usuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null,
) : NSEntity