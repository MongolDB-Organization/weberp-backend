package org.mogoldb.weberpBackend.exception

class DuplicateEntryException(val field: String) : RuntimeException()