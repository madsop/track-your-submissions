package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.talks.TalkID


data class Submission(
        val id: SubmissionID,
        val conference: String,
        val time: String,
        val talk: TalkID,
        var status: Status,
        var notes: String
) {
    constructor(submissionRequest: SubmissionRequest): this(
            id = SubmissionID(),
            conference = submissionRequest.conference,
            time = submissionRequest.time,
            talk = TalkID(submissionRequest.talk),
            status = Status.IN_EVALUATION,
            notes = submissionRequest.notes)
}