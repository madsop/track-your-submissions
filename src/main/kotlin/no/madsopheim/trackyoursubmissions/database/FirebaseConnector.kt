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
import java.io.IOException
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class FirebaseConnector(@ConfigProperty(name = "pathToCredentials") val pathToCredentials: String) {

    lateinit var db: Firestore

    @PostConstruct
    fun setup() {
        FirebaseApp.initializeApp(FirebaseOptions.Builder().setCredentials(getCredentials()).build())
        db = FirestoreClient.getFirestore()
    }

    private fun getCredentials(): GoogleCredentials? = try { GoogleCredentials.getApplicationDefault() } catch (e: IOException) { GoogleCredentials.fromStream(FileInputStream(pathToCredentials)) }

    fun getTalks(): List<Talk> = getCollection().get().get().documents.map { convertToTalk(it) }

    private fun convertToTalk(it: DocumentSnapshot) = Talk(id = TalkID((it.id)), title = it.get("title") as String, cospeaker = it.get("cospeaker") as String)

    fun addTalk(talkRequest: TalkRequest): TalkID = TalkID(getCollection().add(talkRequest).get().id)

    private fun getCollection() = db.collection("talks")

    fun getTalk(talkID: TalkID) : Talk = convertToTalk(getCollection().document(talkID.id).get().get())
}