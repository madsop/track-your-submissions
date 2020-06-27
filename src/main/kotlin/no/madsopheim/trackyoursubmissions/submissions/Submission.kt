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
            id = SubmissionID(),
            conference = submissionRequest.conference,
            time = submissionRequest.time,
            talk = talkResource.getTalk(submissionRequest.talk.id),
            status = Status.IN_EVALUATION,
            notes = submissionRequest.notes)
}