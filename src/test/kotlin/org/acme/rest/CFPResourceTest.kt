package org.acme.rest

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasElement
import com.natpryce.hamkrest.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class CFPResourceTest {

    lateinit var cfpResource: CFPResource

    @BeforeEach
    fun setup() {
        cfpResource = CFPResource()
    }

    @Test
    fun addedSubmissionIsStillThere() {
        cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020, "Talk1"))
        val submissions = cfpResource.getActiveSubmissions()
        assertEquals(submissions.size, 1)
        assertEquals(submissions.get(0).conference, "JBCNConf")
        assertEquals(submissions.get(0).year, 2020)
        assertEquals(submissions.get(0).status, Status.IN_SUBMISSION)
    }

    @Test
    fun markedAsRejectedIsRemovedFromTheActiveList() {
        val submissionID = cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020, "Talk1"))

        cfpResource.markAsRejected(submissionID)

        assertThat(cfpResource.getActiveSubmissions(), equalTo(emptyList()))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveList() {
        val submissionID = cfpResource.addSubmission(SubmissionRequest("Confer", 2020, "Talk2"))

        cfpResource.markAsApproved(submissionID)

        assertThat(cfpResource.getActiveSubmissions().get(0).id, equalTo(submissionID))
    }

    @Test
    fun acceptedTalkIsPresentInTheActiveListAlongWithInSubmissionButNotRejected() {
        val jbcnSubmissionID = cfpResource.addSubmission(SubmissionRequest("JBCNConf", 2020, "Talk2"))
        val conferSubmissionID = cfpResource.addSubmission(SubmissionRequest("Confer", 2020, "Talk2"))
        val rigaSubmissionID = cfpResource.addSubmission(SubmissionRequest("Riga Dev Days", 2020, "Talk2"))

        cfpResource.markAsApproved(conferSubmissionID)
        cfpResource.markAsRejected(jbcnSubmissionID)

        val activeSubmissions = cfpResource.getActiveSubmissions().map { it.id }
        assertThat(activeSubmissions, hasSize(equalTo(2)))
        assertThat(activeSubmissions, hasElement(conferSubmissionID))
        assertThat(activeSubmissions, hasElement(rigaSubmissionID))
    }
}