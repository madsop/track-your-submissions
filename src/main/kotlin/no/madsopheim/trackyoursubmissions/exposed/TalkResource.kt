package no.madsopheim.trackyoursubmissions.exposed

import no.madsopheim.trackyoursubmissions.talks.Talk
import no.madsopheim.trackyoursubmissions.talks.TalkID
import no.madsopheim.trackyoursubmissions.talks.TalkRepository
import no.madsopheim.trackyoursubmissions.talks.TalkRequest
import javax.inject.Inject
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
    @Produces(MediaType.APPLICATION_JSON)
    fun getTalk(uuidRequest: UUIDRequest): Talk

    fun getTalkByTItle(title: String): Talk?
}

@Path("/talks")
class TalkResource(private val talkRepository: TalkRepository) : ITalkResource {

    override fun addTalk(talkRequest: TalkRequest): TalkID {
        return talkRepository.add(talkRequest)
    }

    override fun getTalks(): List<Talk> {
        return talkRepository.getTalks()
    }

    override fun getTalk(uuidRequest: UUIDRequest): Talk {
        return talkRepository.getTalk(TalkID(uuidRequest))
    }

    override fun getTalkByTItle(title: String): Talk? {
        return getTalks().firstOrNull { title.equals(it.title) }
    }
}