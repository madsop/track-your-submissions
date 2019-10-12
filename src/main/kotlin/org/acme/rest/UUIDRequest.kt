package org.acme.rest

import java.util.*


data class UUIDRequest(var id: String) {

    constructor(): this("")

    fun toUUID() = UUID.fromString(id)
}