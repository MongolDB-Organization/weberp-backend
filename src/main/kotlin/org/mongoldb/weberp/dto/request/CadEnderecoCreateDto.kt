package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.mongoldb.weberp.entity.CadCidade
import org.mongoldb.weberp.entity.CadEndereco
import org.mongoldb.weberp.entity.enums.CadEnderecoTipo

class CadEnderecoCreateDto {

    var codigo: Long? = null

    @get: NotNull
    var enderecoTipo: CadEnderecoTipo? = null

    @get: NotNull
    @get: NotBlank
    var logradouro: String? = null

    @get: NotNull
    @get: NotBlank
    var bairro: String? = null

    @get: Length(max = 8)
    @get: NotBlank
    @get: NotNull
    var cep: String? = null

    @get: NotNull
    var cadCidadeCodigo: Long? = null

    companion object {
        fun CadEnderecoCreateDto.toEntity(): CadEndereco {
            val cadEndereco = CadEndereco()
            cadEndereco.codigo = codigo
            cadEndereco.enderecoTipo = enderecoTipo
            cadEndereco.logradouro = logradouro
            cadEndereco.bairro = bairro
            cadEndereco.cep = cep
            cadEndereco.cadCidade = CadCidade()
            cadEndereco.cadCidade?.codigo = cadCidadeCodigo
            return cadEndereco
        }
    }
}