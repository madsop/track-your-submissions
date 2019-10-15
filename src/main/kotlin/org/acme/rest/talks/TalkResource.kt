package org.acme.rest.talks

import org.acme.rest.UUIDRequest
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/talks")
class TalkResource {

    @Inject
    lateinit var talkRepository: TalkRepository

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addTalk(talkRequest: TalkRequest): UUID {
        return talkRepository.add(talkRequest)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalks(): List<Talk> {
        return talkRepository.getTalks()
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalk(uuidRequest: UUIDRequest): Talk {
        return talkRepository.getTalk(uuidRequest)
    }
}