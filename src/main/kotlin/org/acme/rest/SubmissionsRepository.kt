package org.acme.rest

import java.util.*

class SubmissionsRepository {

    private val submissions = mutableListOf<Submission>()

    fun addSubmission(submissionRequest: SubmissionRequest): UUID {
        val submission = Submission(submissionRequest)
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