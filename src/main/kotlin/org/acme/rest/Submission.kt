package org.acme.rest

import java.util.*


data class Submission(
        val id: UUID,
        val conference: String,
        val year: Int,
        val talk: String,
        var status: Status
) {
    constructor(submissionRequest: SubmissionRequest): this(
            UUID.randomUUID(),
            submissionRequest.conference,
            submissionRequest.year,
            submissionRequest.talk,
            Status.IN_SUBMISSION)
}