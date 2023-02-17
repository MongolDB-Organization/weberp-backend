package org.mongoldb.weberp.exception

class NotFoundException(override val message: String? = null) : RuntimeException(message)