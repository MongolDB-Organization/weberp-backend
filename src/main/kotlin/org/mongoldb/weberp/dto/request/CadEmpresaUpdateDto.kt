package org.mongoldb.weberp.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.dto.request.CadEmpresaEnderecoCreateDto.Companion.toEntity
import org.mongoldb.weberp.entity.CadEmpresa
import org.springframework.validation.annotation.Validated

@Validated
class CadEmpresaUpdateDto {

    @get: NotNull
    @get: NotBlank
    var razaoSocial: String? = null

    @get: NotNull
    @get: NotBlank
    var nomeFantasia: String? = null

    @get: NotNull
    @get: NotBlank
    var incricaoEstadual: String? = null

    @get: NotBlank
    var cnpj: String? = null

    @get: NotNull
    @get: NotBlank
    var email: String? = null

    @get:NotBlank
    var telefone: String? = null

    @Valid
    var cadEmpresaEnderecos: List<CadEmpresaEnderecoCreateDto>? = null

    fun toEntity(mergeEntity: CadEmpresa? = null): CadEmpresa {
        val entity = mergeEntity ?: CadEmpresa()
        entity.razaoSocial = razaoSocial ?: ""
        entity.nomeFantasia = nomeFantasia ?: ""
        entity.incricaoEstadual = incricaoEstadual ?: ""
        entity.cnpj = cnpj ?: ""
        entity.email = email ?: ""
        entity.telefone = telefone ?: ""
        entity.cadEmpresaEnderecos = cadEmpresaEnderecos?.map { it -> it.toEntity() }?.toMutableList() ?: mutableListOf()
        return entity
    }
}