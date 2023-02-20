package org.mongoldb.weberp.dto.request

import jakarta.validation.constraints.NotNull
import org.mongoldb.weberp.entity.SisContrato
import org.mongoldb.weberp.entity.Empresa

class EmpresaCreateDto(@get: NotNull var contratoCodigo: Long?) : EmpresaUpdateDto() {

    override fun toEntity(mergeEntity: Empresa?): Empresa {
        val entity = super.toEntity(mergeEntity)
        entity.sisContrato = SisContrato(codigo = contratoCodigo ?: 0)
        return entity
    }
}