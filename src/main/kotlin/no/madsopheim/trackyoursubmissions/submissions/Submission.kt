package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.exposed.TalkResource
import no.madsopheim.trackyoursubmissions.talks.Talk


data class Submission(
        val id: SubmissionID,
        val conference: String,
        val time: String,
        val talk: Talk,
        var status: Status,
        var notes: String
) {
    constructor(submissionRequest: SubmissionRequest, talkResource: TalkResource): this(
            SubmissionID(),
            submissionRequest.conference,
            submissionRequest.time,
            talkResource.getTalk(submissionRequest.talk),
            Status.IN_EVALUATION,
            submissionRequest.notes)
}