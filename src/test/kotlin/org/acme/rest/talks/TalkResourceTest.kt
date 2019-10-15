package org.acme.rest.talks

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.acme.rest.exposed.TalkResource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class TalkResourceTest {

    lateinit var talkResource: TalkResource

    @BeforeEach
    fun setup() {
        talkResource = TalkResource()
        talkResource.talkRepository = TalkRepository()
    }

    @Test
    fun talksAreAdded() {
        val talkRequest = TalkRequest("Talk1")
        talkResource.addTalk(talkRequest)
        assertThat(talkResource.getTalks(), hasSize(equalTo(1)))
        assertThat(talkResource.getTalks().get(0).title, equalTo("Talk1"))
    }
}