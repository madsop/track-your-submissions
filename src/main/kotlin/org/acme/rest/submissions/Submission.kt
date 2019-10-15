package org.acme.rest.submissions

import org.acme.rest.exposed.TalkResource
import org.acme.rest.talks.Talk


data class Submission(
        val id: SubmissionID,
        val conference: String,
        val year: Int,
        val talk: Talk,
        var status: Status,
        var notes: String
) {
    constructor(submissionRequest: SubmissionRequest, talkResource: TalkResource): this(
            SubmissionID(),
            submissionRequest.conference,
            submissionRequest.year,
            talkResource.getTalk(submissionRequest.talk),
            Status.IN_EVALUATION,
            submissionRequest.notes)
}