package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.exposed.UUIDRequest
import java.util.*

data class SubmissionID(val id: String) {

    constructor(): this(UUID.randomUUID().toString())

}
