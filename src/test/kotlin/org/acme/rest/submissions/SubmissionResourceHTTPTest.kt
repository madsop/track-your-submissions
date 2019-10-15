package org.acme.rest.submissions

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
open class SubmissionResourceHTTPTest {

    @Test
    fun canPostSubmission() {
        given().`when`().contentType(ContentType.JSON)
                .body("{\n" +
                        "\t\"title\": \"TalkTitle1\"\n" +
                        "}")
                .post("/talks/add")
        given() //TODO: finn ut korleis chain-e httpkall i dette gwt-verktyget
                .`when`()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\t\"conference\": \"Confer\",\n" +
                        "\t\"year\": 2020,\n" +
                        "\t\"talk\": \"TalkTitle1\"\n" +
                        "}")
                .post("/cfp/add")
                .then()
                .statusCode(200)

    }
}