package no.madsopheim.trackyoursubmissions.talks


data class Talk(val id: TalkID, val title: String, val cospeaker: String?) {
    constructor(talkRequest: TalkRequest) : this(id = TalkID(), title = talkRequest.title, cospeaker = talkRequest.cospeaker)
}