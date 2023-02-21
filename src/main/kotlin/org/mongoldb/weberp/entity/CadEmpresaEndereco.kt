package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "cad_empresa_endereco")
open class CadEmpresaEndereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long? = null

    @ManyToOne(cascade = [CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE])
    @JoinColumn(name = "cad_endereco_codigo",  nullable = false)
    open var cadEndereco: CadEndereco? = null

    @Column(name = "numero", length = 20)
    open var numero: String? = null

    @Column(name = "complemento", length = 100)
    open var complemento: String? = null

    @Column(name = "telefone", length = 50)
    open var telefone: String? = null

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(name = "sis_usuario_atualizacao_codigo")
    open var sisUsuarioAtualizacao: SisUsuario? = null

    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH])
    @JoinColumn(name = "sis_usuario_criacao_codigo")
    open var sisUsuarioCriacao: SisUsuario? = null

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    open var dataCriacao: LocalDateTime? = null

    @Column(nullable = false)
    @UpdateTimestamp
    open var dataModificacao: LocalDateTime? = null

    @ManyToOne(optional = false, targetEntity = CadEmpresa::class)
    @JoinColumn(name = "cad_empresa_codigo", nullable = false)
    open var cadEmpresa: CadEmpresa? = null
}