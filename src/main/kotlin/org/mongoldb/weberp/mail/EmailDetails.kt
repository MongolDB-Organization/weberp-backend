package org.mongoldb.weberp.mail


data class EmailDetails(
    var recipient: String,
    var msgBody: String,
    var subject: String,
    var attachment: String? = null,
)