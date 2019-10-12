package org.acme.rest

import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/cfp")
class CFPResource {

    private val submissions = mutableListOf<Submission>()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
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
    @Path("/reject")
    fun markAsRejected(id: UUIDRequest) {
        submissions
                .filter { it.id == id.toUUID() }
                .forEach { it.status = Status.REJECTED }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    fun markAsApproved(id: UUIDRequest) {
        submissions
                .filter { it.id == id.toUUID() }
                .forEach { it.status = Status.ACCEPTED }
    }
}