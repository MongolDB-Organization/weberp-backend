package org.mogoldb.weberpBackend.controller.v1.empresa

import jakarta.validation.Valid
import org.mogoldb.weberpBackend.delegate.NSController
import org.mogoldb.weberpBackend.entity.Empresa
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.service.ContratoService
import org.mogoldb.weberpBackend.service.EmpresaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("v1/empresas")
class EmpresaController(
    @Autowired override val service: EmpresaService,
    @Autowired private val contratoService: ContratoService,
) : NSController<Empresa>() {

    @PostMapping
    override fun store(@Valid @RequestBody body: Empresa): ResponseEntity<Empresa> {
        val contrato = contratoService.findById(body.contrato!!.codigo!!) ?: throw NotFoundException("Contrato não encontrado")
        val licenca = contrato.licenca ?: throw BadRequestException("Contrato ainda não possui uma licença")
        val dataVencimentoContrato = licenca!!.dataVencimento ?: throw BadRequestException("A lincença do contrato não possui uma data de vencimento")
        if (LocalDateTime.now().isAfter(dataVencimentoContrato)) {
            throw BadRequestException("Licença expirada")
        }
        if (licenca.quantidadeEmpresas!! >= service.buscarQuantidadeEmpresasPorLicenca(licenca.codigo).toInt()) {
            throw BadRequestException("Limite de empresas cadastradas atingido")
        }
        return super.store(body)
    }
}