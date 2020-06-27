package no.madsopheim.trackyoursubmissions.talks

import com.google.cloud.firestore.DocumentSnapshot
import no.madsopheim.trackyoursubmissions.database.Collections
import no.madsopheim.trackyoursubmissions.database.FirebaseConnector
import javax.enterprise.context.ApplicationScoped

interface ITalkRepository {
    fun add(talkRequest: TalkRequest): TalkID
    fun getTalks(): List<Talk>
    fun getTalk(talkID: TalkID): Talk
}

@ApplicationScoped
class TalkRepository(val firebaseConnector: FirebaseConnector) : ITalkRepository {

    override fun add(talkRequest: TalkRequest) = TalkID(firebaseConnector.add(talkRequest, Collections.talks))

    override fun getTalks(): List<Talk> = firebaseConnector.getAll(Collections.talks).map { convertToTalk(it) }

    override fun getTalk(talkID: TalkID) = convertToTalk(firebaseConnector.get(talkID.id, Collections.talks))

    private fun convertToTalk(it: DocumentSnapshot) = Talk(id = TalkID((it.id)), title = it.get("title") as String, cospeaker = it.get("cospeaker") as String)

}