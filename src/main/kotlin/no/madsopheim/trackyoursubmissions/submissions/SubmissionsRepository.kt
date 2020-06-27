package no.madsopheim.trackyoursubmissions.submissions

import com.google.cloud.firestore.DocumentSnapshot
import no.madsopheim.trackyoursubmissions.database.Collections
import no.madsopheim.trackyoursubmissions.database.FirebaseConnector
import no.madsopheim.trackyoursubmissions.talks.TalkID
import javax.enterprise.context.ApplicationScoped

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
class SubmissionsRepository(val firebaseConnector: FirebaseConnector) : ISubmissionsRepository {

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
                talk = TalkID(it.getString("talk") as String),
                status = getStatus(it),
                notes = it.get("notes") as String
        )

    private fun getStatus(ins: DocumentSnapshot) : Status {
        return Status.valueOf(ins.getString("status") ?: Status.IN_EVALUATION.name)
    }

    override fun markAsRejected(submissionID: SubmissionID) = firebaseConnector.updateField(submissionID.id, Collections.submissions, "status", Status.REJECTED.name)

    override fun markAsApproved(submissionID: SubmissionID) = firebaseConnector.updateField(submissionID.id, Collections.submissions, "status", Status.ACCEPTED.name)

    override fun retract(submissionID: SubmissionID) = firebaseConnector.updateField(submissionID.id, Collections.submissions, "status", Status.RETRACTED.name)

    override fun addNotes(submissionID: SubmissionID, notes: String) = firebaseConnector.updateField(submissionID.id, Collections.submissions, "notes", notes)

    override fun getTalk(id: SubmissionID) = convertToSubmission(firebaseConnector.get(id.id, Collections.submissions))
}