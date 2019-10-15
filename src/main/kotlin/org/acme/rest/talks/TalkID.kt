package org.acme.rest.talks

import org.acme.rest.exposed.UUIDRequest
import java.util.*


data class TalkID(val id: UUID) {

    constructor(): this(UUID.randomUUID())
    constructor(uuidRequest: UUIDRequest): this(uuidRequest.toUUID())
}