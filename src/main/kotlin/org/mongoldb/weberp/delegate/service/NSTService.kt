package org.mongoldb.weberp.delegate.service

import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.repository.NSRepository
import org.mongoldb.weberp.entity.SisUsuario
import org.mongoldb.weberp.exception.NotFoundException
import org.mongoldb.weberp.service.impl.LoggedUserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import kotlin.jvm.optionals.getOrNull

abstract class NSTService<OB : NSEntity, DTO, D_DTO, S_DTO>(@Autowired private val repository: NSRepository<OB>) {

    abstract fun toDto(entity: OB): DTO

    abstract fun toDetailedDto(entity: OB): D_DTO

    abstract fun toEntity(dto: S_DTO, mergeEntity: OB?): OB

    enum class NSTServiceSaveType {
        CREATE, UPDATE
    }

    @Autowired
    private lateinit var loggedUserService: LoggedUserServiceImpl

    open fun getLoggedUser(): SisUsuario? = loggedUserService.getLoggedUser()

    open fun findAll(): List<DTO> {
        return repository.findAll().map<OB, DTO> { it ->
            toDto(it)
        }
    }

    @Throws(NotFoundException::class)
    open fun findById(id: Long): D_DTO {
        val optional = repository.findById(id)
        if (!optional.isPresent) {
            throw NotFoundException()
        }
        return toDetailedDto(optional.get())
    }

    fun create(createDto: S_DTO): D_DTO {
        val entity = toEntity(createDto, null)
        beforeCreateAndUpdate(entity, null, NSTServiceSaveType.CREATE)
        return toDetailedDto(repository.save(entity))
    }

    @OptIn(ExperimentalStdlibApi::class)
    open fun update(updateDto: S_DTO, idObject: Long): D_DTO {
        val entityFound = repository.findById(idObject).getOrNull() ?: throw NotFoundException()
        val entity = toEntity(updateDto, entityFound)
        beforeCreateAndUpdate(entity, idObject, NSTServiceSaveType.UPDATE)
        entity.codigo = idObject
        return toDetailedDto(repository.save(entity))
    }

    @kotlin.jvm.Throws(NotFoundException::class)
    open fun beforeCreateAndUpdate(obj: OB, idUpdate: Long?, saveType: NSTServiceSaveType) {
        val loggedUser = getLoggedUser()
        if (saveType == NSTServiceSaveType.CREATE) {
            obj.sisUsuarioCriacao = loggedUser
            obj.sisUsuarioAtualizacao = loggedUser
        } else if (saveType == NSTServiceSaveType.UPDATE) {
            if (!repository.findById(idUpdate!!).isPresent) {
                throw NotFoundException()
            }
            obj.sisUsuarioAtualizacao = loggedUser
        }
    }
}