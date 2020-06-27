package no.madsopheim.trackyoursubmissions.exposed

import no.madsopheim.trackyoursubmissions.submissions.ISubmissionsRepository
import no.madsopheim.trackyoursubmissions.submissions.Submission
import no.madsopheim.trackyoursubmissions.submissions.SubmissionID
import no.madsopheim.trackyoursubmissions.submissions.SubmissionRequest
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/reject")
    fun markAsRejected(id: String)

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/accept")
    fun markAsApproved(id: String)

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/retract")
    fun retract(submissionID: String)

    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/notes/{id}")
    fun addNotes(@PathParam("id") submissionID: String, notes: String)
}

@Path("/cfp")
class SubmissionResource(val submissionsRepository: ISubmissionsRepository) : ISubmissionResource {

    override fun addSubmission(submissionRequest: SubmissionRequest) = submissionsRepository.addSubmission(submissionRequest)

    override fun getActiveSubmissions() = submissionsRepository.getActiveSubmissions()

    override fun markAsRejected(id: String) = submissionsRepository.markAsRejected(SubmissionID(id))

    override fun markAsApproved(id: String) = submissionsRepository.markAsApproved(SubmissionID(id))

    override fun retract(submissionID: String) = submissionsRepository.retract(SubmissionID(submissionID))

    override fun addNotes(submissionID: String, notes: String) = submissionsRepository.addNotes(SubmissionID(submissionID), notes)
}