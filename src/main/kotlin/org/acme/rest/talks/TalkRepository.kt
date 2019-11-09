package org.acme.rest.talks

import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TalkRepository {

    private val talks = mutableMapOf<TalkID, Talk>()

    @PostConstruct
    fun setup() {
        add(TalkRequest("IoT powered by MicroProfile", "Rustam Mehmandarov"))
        add(TalkRequest("No API? Build it yourself!"))
    }

    fun add(talkRequest: TalkRequest): TalkID {
        val talk = Talk(talkRequest)
        talks[talk.id] = talk
        return talk.id
    }

    fun getTalks(): MutableCollection<Talk> {
        return talks.values
    }

    fun getTalk(talkID: TalkID): Talk {
        return talks[talkID]!!
    }


}