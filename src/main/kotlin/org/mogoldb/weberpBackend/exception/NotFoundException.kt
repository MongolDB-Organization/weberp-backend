package org.mogoldb.weberpBackend.exception

class NotFoundException(override val message: String? = null) : RuntimeException(message)