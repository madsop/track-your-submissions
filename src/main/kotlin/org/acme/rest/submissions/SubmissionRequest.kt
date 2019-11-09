package org.acme.rest.submissions

import org.acme.rest.exposed.UUIDRequest


data class SubmissionRequest(
        var conference: String,
        var time: String,
        var talk: UUIDRequest,
        var notes: String
) {
    constructor() : this("","", UUIDRequest(""), "")
}