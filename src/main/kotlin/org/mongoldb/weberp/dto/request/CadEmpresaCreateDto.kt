package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.entity.CadEmpresa

class CadEmpresaCreateDto(@get: NotNull var sisContratoCodigo: Long?) : CadEmpresaUpdateDto() {

    override fun toEntity(mergeEntity: CadEmpresa?): CadEmpresa {
        val entity = super.toEntity(mergeEntity)
        entity.sisContrato = SisContrato(codigo = sisContratoCodigo ?: 0)
        return entity
    }
}