package org.mongoldb.weberp.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.mongoldb.weberp.entity.enums.CadEnderecoTipo
import java.time.LocalDateTime

@Entity
@Table(name = "cad_endereco")
open class CadEndereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var codigo: Long? = null

    @Enumerated
    @Column(name = "endereco_tipo", nullable = false)
    open var enderecoTipo: CadEnderecoTipo? = null

    @Column(name = "logradouro", nullable = false)
    open var logradouro: String? = null

    @Column(name = "bairro", nullable = false)
    open var bairro: String? = null

    @Column(name = "cep", nullable = false, length = 8)
    open var cep: String? = null

    @ManyToOne(cascade = [CascadeType.REFRESH], optional = false)
    @JoinColumn(name = "cad_cidade_codigo", nullable = false)
    open var cadCidade: CadCidade? = null

    @OneToOne
    open var sisUsuarioAtualizacao: SisUsuario? = null

    @OneToOne
    open var sisUsuarioCriacao: SisUsuario? = null

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    open var dataCriacao: LocalDateTime? = null

    @Column(nullable = false)
    @UpdateTimestamp
    open var dataModificacao: LocalDateTime? = null
}