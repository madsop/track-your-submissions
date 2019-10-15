package org.acme.rest.submissions

import org.acme.rest.exposed.TalkResource
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SubmissionsRepository {

    private val submissions = mutableListOf<Submission>()

    @Inject
    lateinit var talkResource: TalkResource

    fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID {
        val submission = Submission(submissionRequest, talkResource)
        submissions.add(submission)
        return submission.id
    }

    fun getActiveSubmissions(): List<Submission> {
        return submissions.filter { it.status != Status.REJECTED }.filter { it.status != Status.RETRACTED }
    }

    fun markAsRejected(id: SubmissionID) {
        getTalk(id).forEach { it.status = Status.REJECTED }
    }

    fun markAsApproved(id: SubmissionID) {
        getTalk(id).forEach { it.status = Status.ACCEPTED }
    }

    private fun getTalk(id: SubmissionID) = submissions.filter { it.id == id }

    fun retract(submissionID: SubmissionID) {
        getTalk(submissionID).forEach { it.status = Status.RETRACTED }
    }
}