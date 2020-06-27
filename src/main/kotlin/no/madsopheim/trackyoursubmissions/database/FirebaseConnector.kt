package no.madsopheim.trackyoursubmissions.database

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.Firestore
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
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

    fun add(obj: Any, collection: Collections) = db.collection(collection.name).add(obj).get().id

    fun get(id: String, collection: Collections) = db.collection(collection.name).document(id).get().get()

    fun getAll(collection: Collections) = db.collection(collection.name).get().get().documents

    fun updateField(id: String, collection: Collections,field: String, newValue: String) {
        db.collection(collection.name).document(id).update(field, newValue)
    }
}