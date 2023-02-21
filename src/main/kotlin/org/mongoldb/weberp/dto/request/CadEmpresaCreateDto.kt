package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.dto.request.CadEmpresaEnderecoCreateDto.Companion.toEntity
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.entity.CadEmpresa
import org.springframework.validation.annotation.Validated

@Validated
class CadEmpresaCreateDto : CadEmpresaUpdateDto() {

    @get: NotNull
    var sisContratoCodigo: Long? = null


    override fun toEntity(mergeEntity: CadEmpresa?): CadEmpresa {
        val entity = super.toEntity(mergeEntity)
        entity.sisContrato = SisContrato(codigo = sisContratoCodigo ?: 0)
        entity.cadEmpresaEnderecos = cadEmpresaEnderecos?.map { it -> it.toEntity() }?.toMutableList() ?: mutableListOf()
        return entity
    }
}