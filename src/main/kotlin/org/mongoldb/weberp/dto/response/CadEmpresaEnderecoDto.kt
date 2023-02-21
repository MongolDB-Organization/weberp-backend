package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.dto.response.CadEnderecoDto.Companion.toDto
import org.mongoldb.weberp.entity.CadEmpresaEndereco

data class CadEmpresaEnderecoDto(
    var codigo: Long? = null,
    var cadEndereco: CadEnderecoDto? = null,
    var numero: String? = null,
    var complemento: String? = null,
    var telefone: String? = null,
) {

    companion object {
        fun CadEmpresaEndereco.toDto(): CadEmpresaEnderecoDto {
            val dto = CadEmpresaEnderecoDto()
            dto.codigo = codigo
            dto.cadEndereco = cadEndereco?.toDto()
            dto.numero = numero
            dto.complemento = complemento
            dto.telefone = telefone
            return dto
        }
    }
}