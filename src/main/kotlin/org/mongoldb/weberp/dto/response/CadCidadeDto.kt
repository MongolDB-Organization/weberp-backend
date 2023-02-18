package org.mongoldb.weberp.dto.response

import org.mongoldb.weberp.entity.CadCidade

open class CadCidadeDto(
    var codigo: Long = 0,
    var ibge: Long = 0,
    var descricao: String = "",
    var uf: String = ""
) {
    companion object {
        fun CadCidade.toDto(): CadCidadeDto {
            val dto = CadCidadeDto()
            dto.codigo = codigo
            dto.ibge = ibge
            dto.descricao = descricao
            dto.uf = cadEstado.uf
            return dto
        }
    }
}