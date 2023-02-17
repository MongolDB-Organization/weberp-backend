package org.mogoldb.weberpBackend.delegate

import org.mongoldb.weberp.delegate.entity.NSEntity
import org.mongoldb.weberp.delegate.endpoint.*

abstract class NSController<OB : NSEntity> : NSIndexEndpoint<OB>, NSShowEndpoint<OB>, NSStoreEndpoint<OB>, NSUpdateEndpoint<OB>, NSDeleteEndpoint<OB>