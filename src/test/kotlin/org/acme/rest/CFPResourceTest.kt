package org.acme.rest

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
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
        cfpResource.addSubmission("JBCNConf", 2020)
        val submissions = cfpResource.getActiveSubmissions()
        assertEquals(submissions.size, 1)
        assertEquals(submissions.get(0).conference, "JBCNConf")
        assertEquals(submissions.get(0).year, 2020)
        assertEquals(submissions.get(0).status, Status.IN_SUBMISSION)
    }

    @Test
    fun markedAsRejectedIsRemovedFromTheActiveList() {
        val submissionID = cfpResource.addSubmission("JBCNConf", 2020)
        cfpResource.markAsRejected(submissionID)

        assertThat(cfpResource.getActiveSubmissions(), equalTo(emptyList()))
    }
}