package no.madsopheim.trackyoursubmissions.exposed

import no.madsopheim.trackyoursubmissions.talks.Talk
import no.madsopheim.trackyoursubmissions.talks.TalkID
import no.madsopheim.trackyoursubmissions.talks.TalkRepository
import no.madsopheim.trackyoursubmissions.talks.TalkRequest
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

interface ITalkResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    fun addTalk(talkRequest: TalkRequest): TalkID

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalks(): List<Talk>

    @GET
    @Path("/talk")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalk(id: TalkID): Talk

    @GET
    @Path("/{title}")
    fun getTalkByTItle(@PathParam("title") title: String): Talk?
}

@Path("/talks")
class TalkResource(private val talkRepository: TalkRepository) : ITalkResource {

    override fun addTalk(talkRequest: TalkRequest): TalkID {
        return talkRepository.add(talkRequest)
    }

    override fun getTalks(): List<Talk> {
        return talkRepository.getTalks()
    }

    override fun getTalk(id: TalkID) = talkRepository.getTalk(id)

    override fun getTalkByTItle(title: String): Talk? {
        return getTalks().firstOrNull { title.equals(it.title) }
    }
}