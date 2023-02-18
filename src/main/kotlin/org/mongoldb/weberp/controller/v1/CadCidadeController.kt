package org.mongoldb.weberp.controller.v1

import org.mongoldb.weberp.dto.response.CadCidadeDetailedDto
import org.mongoldb.weberp.dto.response.CadCidadeDto
import org.mongoldb.weberp.dto.response.PageableDto
import org.mongoldb.weberp.service.impl.CadCidadeServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/cad-cidades")
class CadCidadeController {

    @Autowired
    private lateinit var service: CadCidadeServiceImpl

    @GetMapping
    fun index(
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
        @RequestParam("descricao") descricao: String?,
        @RequestParam("uf") uf: String?
    ): ResponseEntity<PageableDto<CadCidadeDto>> {
        return ResponseEntity.ok(service.findAll(page, size, descricao, uf))
    }

    @GetMapping("{id}")
    fun show(@PathVariable(name = "id") id: Long): ResponseEntity<CadCidadeDetailedDto> {
        return ResponseEntity.ok(service.findById(id))
    }
}