package no.madsopheim.trackyoursubmissions.talks

import no.madsopheim.trackyoursubmissions.database.FirebaseConnector
import javax.enterprise.context.ApplicationScoped

interface ITalkRepository {
    fun add(talkRequest: TalkRequest): TalkID
    fun getTalks(): List<Talk>
    fun getTalk(talkID: TalkID): Talk
}

@ApplicationScoped
class TalkRepository(val firebaseConnector: FirebaseConnector) : ITalkRepository {

    override fun add(talkRequest: TalkRequest) = firebaseConnector.addTalk(talkRequest)

    override fun getTalks(): List<Talk> = firebaseConnector.getTalks()

    override fun getTalk(talkID: TalkID) = firebaseConnector.getTalk(talkID)


}