package org.acme.rest.exposed

import org.acme.rest.submissions.Submission
import org.acme.rest.submissions.SubmissionRequest
import org.acme.rest.submissions.SubmissionsRepository
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/cfp")
class SubmissionResource {

    @Inject
    internal lateinit var submissionsRepository: SubmissionsRepository

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addSubmission(submissionRequest: SubmissionRequest): UUID {
        return submissionsRepository.addSubmission(submissionRequest)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getActiveSubmissions(): List<Submission> {
        return submissionsRepository.getActiveSubmissions()
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reject")
    fun markAsRejected(id: UUIDRequest) {
        submissionsRepository.markAsRejected(id.toUUID())
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    fun markAsApproved(id: UUIDRequest) {
        submissionsRepository.markAsApproved(id.toUUID())
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retract")
    fun retract(submissionID: UUIDRequest) {
        submissionsRepository.retract(submissionID.toUUID())
    }
}