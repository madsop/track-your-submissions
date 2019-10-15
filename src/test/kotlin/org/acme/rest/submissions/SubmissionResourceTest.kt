package org.acme.rest.submissions

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import org.acme.rest.exposed.SubmissionResource
import org.acme.rest.exposed.TalkResource
import org.acme.rest.exposed.UUIDRequest
import org.acme.rest.talks.TalkID
import org.acme.rest.talks.TalkRepository
import org.acme.rest.talks.TalkRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SubmissionResourceTest {

    lateinit var submissionResource: SubmissionResource

    private lateinit var talk1: TalkID
    private lateinit var talk2: TalkID

    @BeforeEach
    fun setup() {
        submissionResource = SubmissionResource()
        submissionResource.submissionsRepository = SubmissionsRepository()
        val talkResource = TalkResource()
        talkResource.talkRepository = TalkRepository()
        talk1 = talkResource.addTalk(TalkRequest("Talk1"))
        talk2 = talkResource.addTalk(TalkRequest("Talk2"))
        submissionResource.submissionsRepository.talkResource = talkResource

    }

    @Test
    fun addedSubmissionIsStillThere() {
        submissionResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk1.id)))
        val submissions = submissionResource.getActiveSubmissions()
        assertEquals(submissions.size, 1)
        assertEquals(submissions.get(0).conference, "JBCNConf")
        assertEquals(submissions.get(0).year, 2020)
        assertEquals(submissions.get(0).status, Status.IN_EVALUATION)
    }

    @Test
    fun markedAsRejectedIsRemovedFromTheActiveList() {
        val submissionID = submissionResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk1.id)))

        submissionResource.markAsRejected(UUIDRequest(submissionID.id))

        assertThat(submissionResource.getActiveSubmissions(), equalTo(emptyList()))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveList() {
        val submissionID = submissionResource.addSubmission(SubmissionRequest("Confer", 2020, UUIDRequest(talk2.id)))

        submissionResource.markAsApproved(UUIDRequest(submissionID.id))

        assertThat(submissionResource.getActiveSubmissions().get(0).id, equalTo(submissionID))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveListAlongWithInSubmissionButNotRejected() {
        val jbcnSubmissionID = submissionResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk2.id)))
        val conferSubmissionID = submissionResource.addSubmission(SubmissionRequest("Confer", 2020, UUIDRequest(talk2.id)))
        val rigaSubmissionID = submissionResource.addSubmission(SubmissionRequest("Riga Dev Days", 2020, UUIDRequest(talk2.id)))

        submissionResource.markAsApproved(UUIDRequest(conferSubmissionID.id))
        submissionResource.markAsRejected(UUIDRequest(jbcnSubmissionID.id))

        val activeSubmissions = submissionResource.getActiveSubmissions().map { it.id }
        assertThat(activeSubmissions, hasSize(equalTo(2)))
        assertThat(activeSubmissions, hasElement(conferSubmissionID))
        assertThat(activeSubmissions, hasElement(rigaSubmissionID))
    }

    @Test
    fun retractedTalkIsNotConsideredActive() {
        val submissionID = submissionResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk2.id)))
        submissionResource.retract(UUIDRequest(submissionID.id))
        assertThat(submissionResource.getActiveSubmissions(), equalTo(emptyList()))
    }
}