package org.acme.rest.talks

import java.util.*


data class Talk(val id: UUID, val title: String, val cospeaker: String?) {
    constructor(talkRequest: TalkRequest) : this(UUID.randomUUID(), talkRequest.title, talkRequest.cospeaker)
}