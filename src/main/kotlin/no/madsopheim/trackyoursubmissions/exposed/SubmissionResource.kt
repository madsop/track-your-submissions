package no.madsopheim.trackyoursubmissions.exposed

import no.madsopheim.trackyoursubmissions.submissions.Submission
import no.madsopheim.trackyoursubmissions.submissions.SubmissionID
import no.madsopheim.trackyoursubmissions.submissions.SubmissionRequest
import no.madsopheim.trackyoursubmissions.submissions.SubmissionsRepository
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
    fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID {
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
        submissionsRepository.markAsRejected(SubmissionID(id))
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    fun markAsApproved(id: UUIDRequest) {
        submissionsRepository.markAsApproved(SubmissionID(id))
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retract")
    fun retract(submissionID: UUIDRequest) {
        submissionsRepository.retract(SubmissionID(submissionID))
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/notes")
    fun addNotes(submissionID: UUIDRequest, notes: String) {
        submissionsRepository.addNotes(SubmissionID(submissionID), notes)
    }
}