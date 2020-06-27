package no.madsopheim.trackyoursubmissions.talks

import no.madsopheim.trackyoursubmissions.exposed.UUIDRequest
import java.util.*


data class TalkID(val id: UUID) {

    constructor(): this(UUID.randomUUID())
    constructor(uuidRequest: UUIDRequest): this(id = uuidRequest.toUUID())
}