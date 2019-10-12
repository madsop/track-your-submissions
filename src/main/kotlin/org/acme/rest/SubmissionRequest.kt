package org.acme.rest


data class SubmissionRequest(
        var conference: String,
        var year: Int,
        var talk: String
) {
    constructor() : this("",-1, "")
}