package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.exposed.UUIDRequest


data class SubmissionRequest(
        var conference: String,
        var time: String,
        var talk: UUIDRequest,
        var notes: String
) {
    constructor() : this("","", UUIDRequest(""), "")
}