package org.acme.rest.talks

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/talks")
class TalkResource {
    private val talks = mutableListOf<Talk>()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun addTalk(talkRequest: TalkRequest) {
        talks.add(Talk(talkRequest))
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalks(): List<Talk> {
        return talks
    }
}