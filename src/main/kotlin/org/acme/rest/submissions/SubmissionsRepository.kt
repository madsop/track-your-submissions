package org.acme.rest.submissions

import org.acme.rest.UUIDRequest
import org.acme.rest.talks.TalkResource
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class SubmissionsRepository {

    private val submissions = mutableListOf<Submission>()

    @Inject
    lateinit var talkResource: TalkResource

    fun addSubmission(submissionRequest: SubmissionRequest): UUID {
        val submission = Submission(submissionRequest, talkResource)
        submissions.add(submission)
        return submission.id
    }

    fun getActiveSubmissions(): List<Submission> {
        return submissions.filter { it.status != Status.REJECTED }
    }

    fun markAsRejected(id: UUIDRequest) {
        submissions
                .filter { it.id == id.toUUID() }
                .forEach { it.status = Status.REJECTED }
    }

    fun markAsApproved(id: UUIDRequest) {
        submissions
                .filter { it.id == id.toUUID() }
                .forEach { it.status = Status.ACCEPTED }
    }
}