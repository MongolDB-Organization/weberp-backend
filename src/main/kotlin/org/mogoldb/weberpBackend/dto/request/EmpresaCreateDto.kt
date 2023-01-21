package org.mogoldb.weberpBackend.dto.request

import jakarta.validation.constraints.NotNull
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Empresa

class EmpresaCreateDto(@get: NotNull var contratoCodigo: Long?) : EmpresaUpdateDto() {

    override fun toEntity(mergeEntity: Empresa?): Empresa {
        val entity = super.toEntity(mergeEntity)
        entity.contrato = Contrato(codigo = contratoCodigo ?: 0)
        return entity
    }
}