package org.mogoldb.weberpBackend.delegate

import org.mogoldb.weberpBackend.delegate.endpoint.*
import org.mogoldb.weberpBackend.delegate.entity.NSEntity

abstract class NSController<OB : NSEntity> : NSIndexEndpoint<OB>, NSShowEndpoint<OB>, NSStoreEndpoint<OB>, NSUpdateEndpoint<OB>, NSDeleteEndpoint<OB>