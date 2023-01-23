package org.mogoldb.weberpBackend.controller.v1

import org.mogoldb.weberpBackend.dto.response.CidadeUfDetailedDto
import org.mogoldb.weberpBackend.dto.response.CidadeUfDto
import org.mogoldb.weberpBackend.dto.response.PageableDto
import org.mogoldb.weberpBackend.service.impl.CidadeUfServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/cidades-uf")
class CidadeUfController {

    @Autowired
    private lateinit var service: CidadeUfServiceImpl

    @GetMapping
    fun index(
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
        @RequestParam("descricao") descricao: String?,
        @RequestParam("sigla") sigla: String?
    ): ResponseEntity<PageableDto<CidadeUfDto>> {
        return ResponseEntity.ok(service.findAll(page, size, descricao, sigla))
    }

    @GetMapping("{id}")
    fun show(@PathVariable(name = "id") id: Long): ResponseEntity<CidadeUfDetailedDto> {
        return ResponseEntity.ok(service.findById(id))
    }
}