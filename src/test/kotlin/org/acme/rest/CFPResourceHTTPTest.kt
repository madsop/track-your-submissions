package org.acme.rest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.junit.jupiter.api.Test

@QuarkusTest
open class CFPResourceHTTPTest {

    @Test
    fun canPostSubmission() {
        given()
                .`when`()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\t\"conference\": \"Confer\",\n" +
                        "\t\"year\": 2020,\n" +
                        "\t\"talk\": \"TalkTitle1\"\n" +
                        "}")
                .post("/cfp")
                .then()
                .statusCode(200)

    }
}