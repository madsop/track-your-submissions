package no.madsopheim.trackyoursubmissions.talks

import no.madsopheim.trackyoursubmissions.database.FirebaseConnector
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TalkRepository(val firebaseConnector: FirebaseConnector) {

    @PostConstruct
    fun setup() {
      //  add(TalkRequest("IoT powered by MicroProfile", "Rustam Mehmandarov"))
        //add(TalkRequest("No API? Build it yourself!"))
    }

    fun add(talkRequest: TalkRequest) = firebaseConnector.addTalk(talkRequest)

    fun getTalks(): List<Talk> = firebaseConnector.getTalks()

    fun getTalk(talkID: TalkID) = firebaseConnector.getTalk(talkID)


}