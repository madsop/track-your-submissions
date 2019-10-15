package org.acme.rest.talks


data class Talk(val id: TalkID, val title: String, val cospeaker: String?) {
    constructor(talkRequest: TalkRequest) : this(TalkID(), talkRequest.title, talkRequest.cospeaker)
}