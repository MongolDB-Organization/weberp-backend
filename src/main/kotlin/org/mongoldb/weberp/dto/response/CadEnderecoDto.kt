package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadCidadeDto.Companion.toDto
import org.mongoldb.weberp.entity.CadEndereco
import org.mongoldb.weberp.entity.enums.CadEnderecoTipo

data class CadEnderecoDto(
    var codigo: Long? = null,
    var enderecoTipo: CadEnderecoTipo? = null,
    var logradouro: String? = null,
    var bairro: String? = null,
    var cep: String? = null,
    var cadCidade: CadCidadeDto? = null,
) {

    companion object {
        fun CadEndereco.toDto(): CadEnderecoDto {
            val dto = CadEnderecoDto()
            dto.codigo = codigo
            dto.enderecoTipo = enderecoTipo
            dto.logradouro = logradouro
            dto.bairro = bairro
            dto.cep = cep
            dto.cadCidade = cadCidade?.toDto()
            return dto
        }
    }
}