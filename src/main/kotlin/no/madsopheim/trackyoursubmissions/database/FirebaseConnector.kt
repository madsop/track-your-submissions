package no.madsopheim.trackyoursubmissions.database

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import no.madsopheim.trackyoursubmissions.talks.Talk
import no.madsopheim.trackyoursubmissions.talks.TalkID
import no.madsopheim.trackyoursubmissions.talks.TalkRequest
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.FileInputStream
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FirebaseConnector(@ConfigProperty(name = "pathToCredentials") val pathToCredentials: String) {

    lateinit var db: Firestore

    private val talks = mutableMapOf<TalkID, Talk>()

    @PostConstruct
    fun setup() {
        val fileInputStream = FileInputStream(pathToCredentials)
        val credentials = GoogleCredentials.fromStream(fileInputStream)
        val options = FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build()
        FirebaseApp.initializeApp(options)

        db = FirestoreClient.getFirestore()
    }

    fun getTalks(): List<Talk> {
        println(db)
        return getCollection().get()
                .get()
                .documents
                .map { convertToTalk(it) }
    }

    private fun convertToTalk(it: DocumentSnapshot) = Talk(id = TalkID((it.id)), title = it.get("title") as String, cospeaker = it.get("cospeaker") as String)

    fun addTalk(talkRequest: TalkRequest): TalkID {
        val add = getCollection().add(talkRequest)
        return TalkID(add.get().id)
    }

    private fun getCollection() = db.collection("talks")

    fun getTalk(talkID: TalkID) : Talk {
        val it : DocumentSnapshot = getCollection().document(talkID.id).get().get()
        return convertToTalk(it)
    }
}