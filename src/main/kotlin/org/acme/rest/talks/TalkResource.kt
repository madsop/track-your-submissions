package org.acme.rest.talks

import org.acme.rest.UUIDRequest
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/talks")
class TalkResource {
    private val talks = mutableListOf<Talk>()

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addTalk(talkRequest: TalkRequest): UUID {
        val talk = Talk(talkRequest)
        talks.add(talk)
        return talk.id
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalks(): List<Talk> {
        return talks
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalk(uuidRequest: UUIDRequest): Talk {
        return talks.first { it.id == uuidRequest.toUUID() }
    }
}