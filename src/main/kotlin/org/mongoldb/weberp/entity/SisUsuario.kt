package org.mongoldb.weberp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.delegate.entity.NSEntity
import java.time.LocalDateTime

@Entity
@Table(name = "sis_usuario")
open class SisUsuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var codigo: Long = 0L,

    @Column(nullable = false)
    open var nome: String? = null,

    @Column(unique = true, nullable = false)
    open var email: String? = null,

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    open var senha: String? = null,

    open var telefone: String? = null,

    @Column(nullable = false)
    open var administrador: Boolean = false,

    @Column(unique = true, length = 8)
    open var codigoVerificacao: String? = null,

    @Column(nullable = false)
    open var verificado: Boolean = false,

    @ManyToMany(targetEntity = SisContrato::class, mappedBy = "sisUsuarios", fetch = FetchType.LAZY)
    open var sisContratos: MutableList<SisContrato> = arrayListOf<SisContrato>(),

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    override var sisUsuarioAtualizacao: SisUsuario? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    override var sisUsuarioCriacao: SisUsuario? = null,

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    override var dataCriacao: LocalDateTime? = null,

    @Column(nullable = false)
    @UpdateTimestamp
    override var dataModificacao: LocalDateTime? = null
) : NSEntity