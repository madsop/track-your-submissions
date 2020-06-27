package no.madsopheim.trackyoursubmissions.submissions

import com.google.cloud.firestore.DocumentSnapshot
import no.madsopheim.trackyoursubmissions.database.Collections
import no.madsopheim.trackyoursubmissions.database.FirebaseConnector
import no.madsopheim.trackyoursubmissions.exposed.TalkResource
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

interface ISubmissionsRepository {
    fun addSubmission(submissionRequest: SubmissionRequest): SubmissionID
    fun getActiveSubmissions(): List<Submission>
    fun markAsRejected(submissionID: SubmissionID)
    fun markAsApproved(submissionID: SubmissionID)
    fun retract(submissionID: SubmissionID)
    fun addNotes(submissionID: SubmissionID, notes: String)
    fun getTalk(id: SubmissionID): Submission
}

@ApplicationScoped
class SubmissionsRepository(val talkResource: TalkResource, val firebaseConnector: FirebaseConnector) : ISubmissionsRepository {

    override fun addSubmission(submissionRequest: SubmissionRequest) = SubmissionID(firebaseConnector.add(submissionRequest, Collections.submissions))

    override fun getActiveSubmissions() =
        firebaseConnector.getAll(Collections.submissions)
                .map { convertToSubmission(it) }
                .filter { it.status != Status.REJECTED }
                .filter { it.status != Status.RETRACTED }

    private fun convertToSubmission(it: DocumentSnapshot): Submission =
            Submission(
                id = SubmissionID(it.id),
                conference = it.get("conference") as String,
                time = it.get("time") as String,
                talk = talkResource.getTalk(it.get("talk") as String),
                status = Status.valueOf(it.get("status") as String),
                notes = it.get("notes") as String
        )

    override fun markAsRejected(submissionID: SubmissionID) = mutateSubmission(submissionID) { submission -> submission.status = Status.REJECTED }

    override fun markAsApproved(submissionID: SubmissionID) = mutateSubmission(submissionID) { submission -> submission.status = Status.ACCEPTED }


    override fun retract(submissionID: SubmissionID) = mutateSubmission(submissionID) { submission -> submission.status = Status.RETRACTED }

    override fun addNotes(submissionID: SubmissionID, notes: String) = mutateSubmission(submissionID) { submission -> submission.notes = notes}

    override fun getTalk(id: SubmissionID) = convertToSubmission(firebaseConnector.get(id.id, Collections.submissions))

    private fun mutateSubmission(submissionID: SubmissionID, action: (s: Submission) -> Unit) = action.invoke(getTalk(submissionID))
}