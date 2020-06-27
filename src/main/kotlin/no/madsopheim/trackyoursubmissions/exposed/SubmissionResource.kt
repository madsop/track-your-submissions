package no.madsopheim.trackyoursubmissions.exposed

import no.madsopheim.trackyoursubmissions.submissions.Submission
import no.madsopheim.trackyoursubmissions.submissions.SubmissionID
import no.madsopheim.trackyoursubmissions.submissions.SubmissionRequest
import no.madsopheim.trackyoursubmissions.submissions.SubmissionsRepository
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

interface ISubmissionResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getActiveSubmissions(): List<Submission>

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reject")
    fun markAsRejected(id: UUIDRequest)

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/accept")
    fun markAsApproved(id: UUIDRequest)

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/retract")
    fun retract(submissionID: UUIDRequest)

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/notes")
    fun addNotes(submissionID: UUIDRequest, notes: String)
}

@Path("/cfp")
class SubmissionResource(val submissionsRepository: SubmissionsRepository) : ISubmissionResource {

    override fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID {
        return submissionsRepository.addSubmission(submissionRequest)
    }

    override fun getActiveSubmissions(): List<Submission> {
        return submissionsRepository.getActiveSubmissions()
    }

    override fun markAsRejected(id: UUIDRequest) {
        submissionsRepository.markAsRejected(SubmissionID(id))
    }

    override fun markAsApproved(id: UUIDRequest) {
        submissionsRepository.markAsApproved(SubmissionID(id))
    }

    override fun retract(submissionID: UUIDRequest) {
        submissionsRepository.retract(SubmissionID(submissionID))
    }

    override fun addNotes(submissionID: UUIDRequest, notes: String) {
        submissionsRepository.addNotes(SubmissionID(submissionID), notes)
    }
}