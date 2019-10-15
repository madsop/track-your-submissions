package org.acme.rest

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import org.acme.rest.talks.TalkRequest
import org.acme.rest.talks.TalkResource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class CFPResourceTest {

    lateinit var cfpResource: CFPResource

    lateinit var talk1: UUID
    lateinit var talk2: UUID

    @BeforeEach
    fun setup() {
        cfpResource = CFPResource()
        cfpResource.submissionsRepository = SubmissionsRepository()
        val talkResource = TalkResource()
        talk1 = talkResource.addTalk(TalkRequest("Talk1"))
        talk2 = talkResource.addTalk(TalkRequest("Talk2"))
        cfpResource.submissionsRepository.talkResource = talkResource

    }

    @Test
    fun addedSubmissionIsStillThere() {
        cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020,  UUIDRequest(talk1)))
        val submissions = cfpResource.getActiveSubmissions()
        assertEquals(submissions.size, 1)
        assertEquals(submissions.get(0).conference, "JBCNConf")
        assertEquals(submissions.get(0).year, 2020)
        assertEquals(submissions.get(0).status, Status.IN_SUBMISSION)
    }

    @Test
    fun markedAsRejectedIsRemovedFromTheActiveList() {
        val submissionID = cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk1)))

        cfpResource.markAsRejected(UUIDRequest(submissionID))

        assertThat(cfpResource.getActiveSubmissions(), equalTo(emptyList()))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveList() {
        val submissionID = cfpResource.addSubmission(SubmissionRequest("Confer", 2020, UUIDRequest(talk2)))

        cfpResource.markAsApproved(UUIDRequest(submissionID))

        assertThat(cfpResource.getActiveSubmissions().get(0).id, equalTo(submissionID))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveListAlongWithInSubmissionButNotRejected() {
        val jbcnSubmissionID = cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020, UUIDRequest(talk2)))
        val conferSubmissionID = cfpResource.addSubmission(SubmissionRequest("Confer", 2020, UUIDRequest(talk2)))
        val rigaSubmissionID = cfpResource.addSubmission(SubmissionRequest("Riga Dev Days", 2020, UUIDRequest(talk2)))

        cfpResource.markAsApproved(UUIDRequest(conferSubmissionID.toString()))
        cfpResource.markAsRejected(UUIDRequest(jbcnSubmissionID))

        val activeSubmissions = cfpResource.getActiveSubmissions().map { it.id }
        assertThat(activeSubmissions, hasSize(equalTo(2)))
        assertThat(activeSubmissions, hasElement(conferSubmissionID))
        assertThat(activeSubmissions, hasElement(rigaSubmissionID))
    }
}