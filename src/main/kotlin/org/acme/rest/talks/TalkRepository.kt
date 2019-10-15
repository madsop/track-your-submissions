package org.acme.rest.talks

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TalkRepository {

    private val talks = mutableListOf<Talk>()

    fun add(talkRequest: TalkRequest): TalkID {
        val talk = Talk(talkRequest)
        talks.add(talk)
        return talk.id
    }

    fun getTalks(): List<Talk> {
        return talks
    }

    fun getTalk(talkID: TalkID): Talk {
        return talks.first { it.id == talkID }
    }


}