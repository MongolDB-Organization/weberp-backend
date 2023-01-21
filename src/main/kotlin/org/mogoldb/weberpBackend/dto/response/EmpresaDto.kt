package org.mogoldb.weberpBackend.dto.response

import org.mogoldb.weberpBackend.entity.Empresa

open class EmpresaDto(
    var codigo: Long = 0,
    var razaoSocial: String? = null,
    var nomeFantasia: String? = null,
    var incricaoEstadual: String? = null,
    var cnpj: String? = null,
    var email: String? = null,
    var telefone: String? = null,
) {
    companion object {
        fun Empresa.toDto(): EmpresaDto {
            val dto = EmpresaDto()
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