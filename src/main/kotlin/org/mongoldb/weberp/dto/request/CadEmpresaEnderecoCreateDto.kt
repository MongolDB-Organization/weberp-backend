package org.mongoldb.weberp.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.Length
import org.mongoldb.weberp.dto.request.CadEnderecoCreateDto.Companion.toEntity
import org.mongoldb.weberp.entity.CadEmpresaEndereco
import org.springframework.validation.annotation.Validated

@Validated
class CadEmpresaEnderecoCreateDto {

    var codigo: Long? = null

    @Valid
    @get: NotNull
    var cadEndereco: CadEnderecoCreateDto? = null

    @get: NotNull
    @get: NotBlank
    @get: Length(max = 15)
    var numero: String? = null

    @get: Length(max = 100)
    @get: NotBlank
    var complemento: String? = null

    @get: Length(max = 15)
    @get: NotBlank
    var telefone: String? = null

    companion object {
        fun CadEmpresaEnderecoCreateDto.toEntity() : CadEmpresaEndereco {
            val cadEmpresaEndereco = CadEmpresaEndereco()
            cadEmpresaEndereco.codigo  = codigo
            cadEmpresaEndereco.cadEndereco  = cadEndereco?.toEntity()
            cadEmpresaEndereco.numero = numero
            cadEmpresaEndereco.complemento = complemento
            cadEmpresaEndereco.telefone = telefone
            return cadEmpresaEndereco
        }
    }
}
