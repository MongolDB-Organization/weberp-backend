package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadCidade

open class CadCidadeDto(
    var codigo: Long? = null,
    var ibge: Long? = null,
    var descricao: String? = null,
    var uf: String? = null
) {
    companion object {
        fun CadCidade.toDto(): CadCidadeDto {
            val dto = CadCidadeDto()
            dto.codigo = codigo
            dto.ibge = ibge
            dto.descricao = descricao
            dto.uf = cadEstado?.uf
            return dto
        }
    }
}