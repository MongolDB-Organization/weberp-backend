package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.dto.request.ContratoCreateUpdateDto
import org.mogoldb.weberpBackend.dto.response.ContratoDetailedDto
import org.mogoldb.weberpBackend.dto.response.ContratoDto
import org.mogoldb.weberpBackend.delegate.service.NSTService
import org.mogoldb.weberpBackend.dto.mapper.ContratoMapper.Companion.toDto
import org.mogoldb.weberpBackend.dto.mapper.ContratoMapper.Companion.toDetailedDto
import org.mogoldb.weberpBackend.dto.mapper.ContratoMapper.Companion.toEntity
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.exception.BadRequestException
import org.mogoldb.weberpBackend.exception.NoPermitionException
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.ContratoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class ContratoService(@Autowired private val repository: ContratoRepository) : NSTService<Contrato, ContratoDto, ContratoDetailedDto, ContratoCreateUpdateDto>(repository) {

    @Autowired
    private lateinit var usuarioService: UsuarioService

    override fun toDto(entity: Contrato): ContratoDto = entity.toDto()

    override fun toDetailedDto(entity: Contrato): ContratoDetailedDto = entity.toDetailedDto()

    override fun toEntity(dto: ContratoCreateUpdateDto, mergeEntity: Contrato?): Contrato = dto.toEntity(mergeEntity)

    override fun findAll(): List<ContratoDto> {
        return super.findAll()
    }

    @Throws(BadRequestException::class)
    override fun findById(id: Long): ContratoDetailedDto {
        val loggedUser = getLoggedUser()
        if (loggedUser!!.administrador) {
            return super.findById(id)
        }
        if (usuarioService.getContratosCodigos(loggedUser!!).contains(loggedUser?.codigo)) {
            return super.findById(id)
        }
        val contratoOptionalFound =  repository.findById(id)
        if (!contratoOptionalFound.isPresent) {
            throw NotFoundException()
        }
        if (contratoOptionalFound.get().usuarioProprietario?.codigo != loggedUser?.codigo) {
            throw NoPermitionException()
        }
        return toDetailedDto(contratoOptionalFound.get())
    }

    @Throws(BadRequestException::class)
    override fun beforeCreateAndUpdate(obj: Contrato, idUpdate: Long?, saveType: NSTServiceSaveType) {
        val loggedUser = getLoggedUser()
        if (saveType == NSTServiceSaveType.CREATE) {
            obj.usuarioProprietario = loggedUser
            obj.usuarios.add(loggedUser!!)
        }
        if (saveType == NSTServiceSaveType.UPDATE) {
            if (obj.usuarioProprietario?.codigo != loggedUser!!.codigo || loggedUser.administrador) {
                throw BadRequestException("Contrato só pode ser editado pelo proprietário")
            }
        }
        super.beforeCreateAndUpdate(obj, idUpdate, saveType)
    }
}
