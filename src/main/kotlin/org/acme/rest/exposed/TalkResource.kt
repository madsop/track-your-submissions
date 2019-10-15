package org.acme.rest.exposed

import org.acme.rest.talks.Talk
import org.acme.rest.talks.TalkID
import org.acme.rest.talks.TalkRepository
import org.acme.rest.talks.TalkRequest
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/talks")
class TalkResource {

    @Inject
    lateinit var talkRepository: TalkRepository

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addTalk(talkRequest: TalkRequest): TalkID {
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
        return talkRepository.getTalk(TalkID(uuidRequest))
    }
}