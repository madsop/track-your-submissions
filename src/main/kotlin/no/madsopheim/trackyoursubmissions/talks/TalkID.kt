package no.madsopheim.trackyoursubmissions.talks

import no.madsopheim.trackyoursubmissions.exposed.UUIDRequest
import java.util.*


data class TalkID(val id: String) {

    constructor(): this(UUID.randomUUID().toString())
}