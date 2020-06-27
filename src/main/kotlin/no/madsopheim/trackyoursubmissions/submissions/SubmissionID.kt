package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.exposed.UUIDRequest
import java.util.*

data class SubmissionID(val id: UUID) {

    constructor(): this(UUID.randomUUID())
    constructor(uuidRequest: UUIDRequest): this(uuidRequest.toUUID())

}
