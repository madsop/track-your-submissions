package org.acme.rest.talks

import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TalkRepository {

    private val talks = mutableListOf<Talk>()

    fun add(talkRequest: TalkRequest): UUID {
        val talk = Talk(talkRequest)
        talks.add(talk)
        return talk.id
    }

    fun getTalks(): List<Talk> {
        return talks
    }

    fun getTalk(uuidRequest: UUID): Talk {
        return talks.first { it.id == uuidRequest }
    }


}