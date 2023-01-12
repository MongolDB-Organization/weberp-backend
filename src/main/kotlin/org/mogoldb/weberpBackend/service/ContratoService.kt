package org.mogoldb.weberpBackend.service

import org.mogoldb.weberpBackend.delegate.service.NSService
import org.mogoldb.weberpBackend.entity.Contrato
import org.mogoldb.weberpBackend.entity.Usuario
import org.mogoldb.weberpBackend.exception.NotFoundException
import org.mogoldb.weberpBackend.repository.ContratoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContratoService(@Autowired private val repository: ContratoRepository) : NSService<Contrato>(repository) {


    @Autowired
    private lateinit var usuarioService: UsuarioService

    fun findUsuariosByContratos(usuario: Usuario): List<Contrato> {
        return repository.findAll()
    }

    override fun findById(id: Long): Contrato? {
        val loggedUser = getLoggedUser()
        val contratosCodigos = usuarioService.getContratosCodigos(loggedUser!!)
        if (contratosCodigos.contains(loggedUser.codigo)) {
            throw NotFoundException()
        }
        return super.findById(id)
    }

    override fun afterCreateAndUpdate(obj: Contrato, idUpdate: Long?, saveType: NSServiceSaveType) {
        val loggedUser = getLoggedUser()
        if (saveType == NSServiceSaveType.CREATE) {
            obj.usuarioProprietario = loggedUser
            obj.usuarios = hashSetOf(loggedUser!!)
        }
        super.afterCreateAndUpdate(obj, idUpdate, saveType)
    }
}
