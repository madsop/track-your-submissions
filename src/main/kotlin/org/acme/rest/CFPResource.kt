package org.acme.rest

import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/cfp")
class CFPResource {

    var submissionsRepository: SubmissionsRepository = SubmissionsRepository()


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
        submissionsRepository.markAsRejected(id)
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    fun markAsApproved(id: UUIDRequest) {
        submissionsRepository.markAsApproved(id)
    }
}