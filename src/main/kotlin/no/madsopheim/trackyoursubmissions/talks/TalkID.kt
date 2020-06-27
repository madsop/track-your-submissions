package no.madsopheim.trackyoursubmissions.talks

import java.util.*


data class TalkID(val id: String) {

    constructor(): this(id = UUID.randomUUID().toString())
}