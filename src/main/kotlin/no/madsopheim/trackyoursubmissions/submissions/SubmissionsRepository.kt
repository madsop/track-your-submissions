package no.madsopheim.trackyoursubmissions.submissions

import no.madsopheim.trackyoursubmissions.exposed.TalkResource
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SubmissionsRepository {


    private val submissions = mutableMapOf<SubmissionID, Submission>()

    @Inject
    lateinit var talkResource: TalkResource

    fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID {
        val submission = Submission(submissionRequest, talkResource)
        submissions[submission.id] = submission
        return submission.id
    }

    fun getActiveSubmissions(): List<Submission> {
        return submissions.values.filter { it.status != Status.REJECTED }.filter { it.status != Status.RETRACTED }
    }

    fun markAsRejected(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.REJECTED }
    }

    fun markAsApproved(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.ACCEPTED }
    }

    fun retract(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.RETRACTED }
    }

    fun addNotes(submissionID: SubmissionID, notes: String) {
        mutateSubmission(submissionID) { submission -> submission.notes = notes}
    }

    private fun getTalk(id: SubmissionID) = submissions[id]!!

    private fun mutateSubmission(submissionID: SubmissionID, action: (s: Submission) -> Unit) {
        val talk = getTalk(submissionID)
        action.invoke(talk)
    }
}