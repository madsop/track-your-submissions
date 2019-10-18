package org.acme.rest.submissions

import org.acme.rest.exposed.TalkResource
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

    fun markAsRejected(id: SubmissionID) {
        getTalk(id).status = Status.REJECTED
    }

    fun markAsApproved(id: SubmissionID) {
        getTalk(id).status = Status.ACCEPTED
    }

    private fun getTalk(id: SubmissionID) = submissions[id]!!

    fun retract(submissionID: SubmissionID) {
        getTalk(submissionID).status = Status.RETRACTED
    }

    fun addNotes(submissionID: SubmissionID, notes: String) {
        getTalk(submissionID).notes += notes
    }
}