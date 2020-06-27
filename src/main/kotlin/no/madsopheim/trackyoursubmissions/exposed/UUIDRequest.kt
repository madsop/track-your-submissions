package no.madsopheim.trackyoursubmissions.exposed

import java.util.*


data class UUIDRequest(var id: String) {

    constructor(): this("")
    constructor(uuid: UUID): this(uuid.toString())

    fun toUUID() = UUID.fromString(id)
}