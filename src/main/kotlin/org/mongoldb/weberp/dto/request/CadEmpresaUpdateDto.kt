package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.CadEmpresa

open class CadEmpresaUpdateDto(
    @get: NotNull @get: NotBlank var razaoSocial: String? = null,

    @get: NotNull @get: NotBlank var nomeFantasia: String? = null,

    @get: NotNull @get: NotBlank var incricaoEstadual: String? = null,

    @get: NotBlank var cnpj: String? = null,

    @get: NotNull @get: NotBlank var email: String? = null,

    @get:NotBlank var telefone: String? = null,
) {
    open fun toEntity(mergeEntity: CadEmpresa? = null): CadEmpresa {
        val entity = mergeEntity ?: CadEmpresa()
        entity.razaoSocial = razaoSocial ?: ""
        entity.nomeFantasia = nomeFantasia ?: ""
        entity.incricaoEstadual = incricaoEstadual ?: ""
        entity.cnpj = cnpj ?: ""
        entity.email = email ?: ""
        entity.telefone = telefone ?: ""
        return entity
    }
}