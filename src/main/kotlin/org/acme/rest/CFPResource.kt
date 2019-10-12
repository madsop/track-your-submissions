package org.acme.rest

import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/cfp")
class CFPResource {

    private val submissions = mutableListOf<Submission>()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun addSubmission(submissionRequest: SubmissionRequest): UUID {
        val submission = Submission(submissionRequest)
        submissions.add(submission)
        return submission.id
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getActiveSubmissions(): List<Submission> {
        return submissions.filter { it.status != Status.REJECTED }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun markAsRejected(id: UUID) {
        submissions
                .filter { it.id == id }
                .forEach { it.status = Status.REJECTED }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    fun markAsApproved(id: UUID) {
        submissions
                .filter { it.id == id }
                .forEach { it.status = Status.ACCEPTED }
    }
}