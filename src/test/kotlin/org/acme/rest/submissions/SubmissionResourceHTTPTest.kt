package org.acme.rest.submissions

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.acme.rest.exposed.UUIDRequest
import org.acme.rest.talks.TalkRequest
import org.junit.jupiter.api.Test

@QuarkusTest
open class SubmissionResourceHTTPTest {

    @Test
    fun canPostSubmission() {
        val talkId: String = given().`when`().contentType(ContentType.JSON)
                .body(TalkRequest(title = "TalkTitle1"))
                .post("/talks/add")
                .then()
                .extract()
                .path("id")
        val request = SubmissionRequest(conference = "Confer", time = "2020", talk = UUIDRequest(talkId), notes = "emptyNotes")

        given()
                .`when`()
                .contentType(ContentType.JSON)
                .body(request)
                .post("/cfp/add")
                .then()
                .statusCode(200)

    }
}