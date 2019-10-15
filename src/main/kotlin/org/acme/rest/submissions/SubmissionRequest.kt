package org.acme.rest.submissions

import org.acme.rest.exposed.UUIDRequest


data class SubmissionRequest(
        var conference: String,
        var year: Int,
        var talk: UUIDRequest,
        var notes: String
) {
    constructor() : this("",-1, UUIDRequest(""), "")
}