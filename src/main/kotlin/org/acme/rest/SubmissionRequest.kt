package org.acme.rest


data class SubmissionRequest(
        var conference: String,
        var year: Int,
        var talk: UUIDRequest
) {
    constructor() : this("",-1, UUIDRequest(""))
}