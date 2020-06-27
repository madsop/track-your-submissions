package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.talks.TalkID


data class SubmissionRequest(
        var conference: String,
        var time: String,
        var talk: String,
        var notes: String
) {
    constructor() : this(conference = "", time = "", talk = "", notes = "")
}