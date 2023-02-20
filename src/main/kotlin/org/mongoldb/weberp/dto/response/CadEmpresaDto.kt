package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadEmpresa

open class CadEmpresaDto(
    var codigo: Long = 0,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null,
    var incricaoEstadual: String? = null,
    var cnpj: String? = null,
    var email: String? = null,
    var telefone: String? = null,
) {
    companion object {
        fun CadEmpresa.toDto(): CadEmpresaDto {
            val dto = CadEmpresaDto()
            dto.codigo = codigo
            dto.razaoSocial = razaoSocial
            dto.nomeFantasia = nomeFantasia
            dto.incricaoEstadual = incricaoEstadual
            dto.cnpj = cnpj
            dto.email = email
            dto.telefone = telefone
            return dto
        }
    }
}