package no.madsopheim.trackyoursubmissions.submissions

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.containsSubstring
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import no.madsopheim.trackyoursubmissions.exposed.SubmissionResource
import no.madsopheim.trackyoursubmissions.exposed.TalkResource
import no.madsopheim.trackyoursubmissions.talks.FakeTalkRepository
import no.madsopheim.trackyoursubmissions.talks.TalkID
import no.madsopheim.trackyoursubmissions.talks.TalkRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SubmissionResourceTest {

    lateinit var submissionResource: SubmissionResource

    private lateinit var talk1: TalkID
    private lateinit var talk2: TalkID

    @BeforeEach
    fun setup() {
        val talkResource = TalkResource(FakeTalkRepository())
        talk1 = talkResource.addTalk(TalkRequest("Talk1"))
        talk2 = talkResource.addTalk(TalkRequest("Talk2"))
        submissionResource = SubmissionResource(FakeSubmissionsRepository())
    }

    @Test
    fun addedSubmissionIsStillThere() {
        createSubmissionRequest("JBCNConf", talk1)
        val submissions = submissionResource.getActiveSubmissions()
        assertEquals(submissions.size, 1)
        assertEquals(submissions.get(0).conference, "JBCNConf")
        assertEquals(submissions.get(0).time, "2020")
        assertEquals(submissions.get(0).status, Status.IN_EVALUATION)
    }

    private fun createSubmissionRequest(conferenceName: String, talk: TalkID): SubmissionID {
        return submissionResource.addSubmission(SubmissionRequest(conferenceName, "2020", talk.id, ""))
    }

    @Test
    fun markedAsRejectedIsRemovedFromTheActiveList() {
        val submissionID = createSubmissionRequest("JBCNConf", talk1)

        submissionResource.markAsRejected(submissionID.id)

        assertThat(submissionResource.getActiveSubmissions(), equalTo(emptyList()))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveList() {
        val submissionID = createSubmissionRequest("Confer", talk2)

        submissionResource.markAsApproved(submissionID.id)

        assertThat(submissionResource.getActiveSubmissions().get(0).id, equalTo(submissionID))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveListAlongWithInSubmissionButNotRejected() {
        val jbcnSubmissionID = createSubmissionRequest("JBCNConf", talk2)
        val conferSubmissionID = createSubmissionRequest("Confer", talk2)
        val rigaSubmissionID = createSubmissionRequest("Riga Dev Days", talk2)

        submissionResource.markAsApproved(conferSubmissionID.id)
        submissionResource.markAsRejected(jbcnSubmissionID.id)

        val activeSubmissions = submissionResource.getActiveSubmissions().map { it.id }
        assertThat(activeSubmissions, hasSize(equalTo(2)))
        assertThat(activeSubmissions, hasElement(conferSubmissionID))
        assertThat(activeSubmissions, hasElement(rigaSubmissionID))
    }

    @Test
    fun retractedTalkIsNotConsideredActive() {
        val submissionID = createSubmissionRequest("JBCNConf", talk2)
        submissionResource.retract(submissionID.id)
        assertThat(submissionResource.getActiveSubmissions(), equalTo(emptyList()))
    }

    @Test
    fun canAddNotesToSubmission() {
        val submissionID = createSubmissionRequest("JBCNConf", talk2)
        submissionResource.addNotes(submissionID.id, "my special notes")
        assertThat(submissionResource.getActiveSubmissions().get(0).notes, containsSubstring("my special notes"))
    }
}