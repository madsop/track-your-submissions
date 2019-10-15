package org.acme.rest.submissions

import org.acme.rest.exposed.UUIDRequest
import java.util.*

data class SubmissionID(val id: UUID) {
    fun toRequest() = UUIDRequest(id)

    constructor(): this(UUID.randomUUID())
    constructor(uuidRequest: UUIDRequest): this(uuidRequest.toUUID())

}
