package no.madsopheim.trackyoursubmissions.submissions

class FakeSubmissionsRepository : ISubmissionsRepository {

    private val submissions = mutableMapOf<SubmissionID, Submission>()

    override fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID {
        val submission = Submission(submissionRequest)
        submissions[submission.id] = submission
        return submission.id
    }

    override fun getActiveSubmissions(): List<Submission> {
        return submissions.values.filter { it.status != Status.REJECTED }.filter { it.status != Status.RETRACTED }
    }

    override fun markAsRejected(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.REJECTED }
    }

    override fun markAsApproved(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.ACCEPTED }
    }

    override fun retract(submissionID: SubmissionID) {
        mutateSubmission(submissionID) { submission -> submission.status = Status.RETRACTED }
    }

    override fun addNotes(submissionID: SubmissionID, notes: String) {
        mutateSubmission(submissionID) { submission -> submission.notes = notes}
    }

    override fun getTalk(id: SubmissionID) = submissions[id]!!

    private fun mutateSubmission(submissionID: SubmissionID, action: (s: Submission) -> Unit) {
        val talk = getTalk(submissionID)
        action.invoke(talk)
    }
}