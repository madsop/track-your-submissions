package no.madsopheim.trackyoursubmissions.talks

class FakeTalkRepository: ITalkRepository {

    val talks = mutableMapOf<TalkID, Talk>()

    override fun add(talkRequest: TalkRequest): TalkID {
        val talkID = TalkID()
        talks[talkID] = Talk(talkRequest)
        return talkID
    }

    override fun getTalks(): List<Talk> = talks.values.toList()

    override fun getTalk(talkID: TalkID) = talks[talkID]!!

}