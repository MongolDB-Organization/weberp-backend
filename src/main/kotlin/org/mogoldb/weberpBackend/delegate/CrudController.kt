package org.mogoldb.weberpBackend.delegate

import org.mogoldb.weberpBackend.delegate.crud.*

abstract class CrudController<OB : DefaultEntity, PK : Long> :
    IndexControllerCrud<OB, PK>,
    ShowControllerCrud<OB, PK>,
    PostControllerCrud<OB, PK>,
    UpdateControllerCrud<OB, PK>,
    DeleteControllerCrud<OB, PK>