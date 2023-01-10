package org.mogoldb.weberpBackend.delegate.entity

import org.mogoldb.weberpBackend.entity.Contrato

interface NSContratoLevelEntity : NSEntity {
    open var contrato: Contrato?
}