package org.acme.rest.submissions

import org.acme.rest.talks.Talk
import org.acme.rest.exposed.TalkResource
import java.util.*


data class Submission(
        val id: UUID,
        val conference: String,
        val year: Int,
        val talk: Talk,
        var status: Status
) {
    constructor(submissionRequest: SubmissionRequest, talkResource: TalkResource): this(
            UUID.randomUUID(),
            submissionRequest.conference,
            submissionRequest.year,
            talkResource.getTalk(submissionRequest.talk),
            Status.IN_EVALUATION)
}