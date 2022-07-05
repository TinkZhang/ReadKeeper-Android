package app.tinks.readkeeper.basic

import android.app.Activity
import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.lifecycle.MutableLiveData
import app.tinks.readkeeper.basic.model.*
import app.tinks.readkeeper.basic.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.*

object UserRepository {
    var user = Firebase.auth.currentUser
    val loginStatus: MutableLiveData<LoginStatus> by lazy {
        MutableLiveData<LoginStatus>()
    }

    val isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    lateinit var lastBook: EditableBook

    private val fictionsMutex = Mutex()
    private var fictions: List<NYTimesBook> = listOf()
    private val nonFictionsMutex = Mutex()
    private var nonFictions: List<NYTimesBook> = listOf()

    val nytCollectionRef = Firebase.firestore.collection("nytBestSellers")

    init {
        if (user == null) {
            loginStatus.value = LoginStatus.Logout
        } else {
            loginStatus.value = LoginStatus.Login
        }
    }

    private val userDocumentRef = if (user == null) {
        Timber.d("offline users")
        Firebase.firestore.document("user/${UUID.randomUUID()}}")
    } else {
        Timber.d("Signed in user with id: ${user!!.uid}")
        Firebase.firestore.document("user/${user!!.uid}")
    }

    val readingCollectionRef = userDocumentRef.collection("readings")
    val wishCollectionRef = userDocumentRef.collection("wishes")
    val archivedCollectionRef = userDocumentRef.collection("archived")

    inline fun <reified T : EditableBook> addBook(book: T) {
        when (T::class) {
            ReadingBook::class -> readingCollectionRef.document(book.bookInfo.uuid).set(book)
            WishBook::class -> wishCollectionRef.document(book.bookInfo.uuid).set(book)
            ArchivedBook::class -> archivedCollectionRef.document(book.bookInfo.uuid).set(book)
            else -> Unit
        }
    }

    fun removeReadingBook(uuid: String) {
        readingCollectionRef.document(uuid).delete()
    }

    fun removeWishBook(uuid: String) {
        wishCollectionRef.document(uuid).delete()
    }

    fun removeArchiveBook(uuid: String) {
        archivedCollectionRef.document(uuid).delete()
    }

    suspend inline fun <reified T : EditableBook> getList(
        reference: CollectionReference,
        page: Int
    ): List<T> {
        return when (page) {
            0 -> {
                val firstPage = reference
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(T::class.java)
                if (firstPage.isNotEmpty()) {
                    lastBook = firstPage.last()
                    Timber.d("ReadingBook: ${firstPage.first()}")
                }
                firstPage
            }
            else -> {
                val books = reference
                    .orderBy(FieldPath.of("timeInfo", "editedTime"), Query.Direction.DESCENDING)
                    .startAfter(lastBook)
                    .limit(PAGE_SIZE.toLong())
                    .get()
                    .await()
                    .toObjects(T::class.java)
                if (books.isNotEmpty()) {
                    lastBook = books.last() as T
                }
                books
            }
        }
    }

    suspend inline fun <reified T : EditableBook> getList(nextPage: Int): List<T> =
        when (T::class) {
            ReadingBook::class -> getList(readingCollectionRef, nextPage)
            WishBook::class -> getList(wishCollectionRef, nextPage)
            ArchivedBook::class -> getList(archivedCollectionRef, nextPage)
            else -> listOf()
        }

    fun signOutWithGoogle() {
        Firebase.auth.signOut()
        loginStatus.value = LoginStatus.Logout
    }

    fun signInWithGoogle(context: Context) {
        loginStatus.value = LoginStatus.Logging
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_token_id))
            .requestEmail()
            .requestProfile()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        val signInIntent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, 9001)
    }

    suspend fun getWeeklyBooks(type: NYBookType): List<NYTimesBook> {
        val books = Firebase.firestore.collection(type.name)
            .orderBy("rank")
            .get().await()
            .toObjects(NYTimesBook::class.java)
        Timber.d("Fetch NYBook from Firebase")
        return if (type == NYBookType.Fictions) {
            fictionsMutex.withLock {
                fictions = books
            }
            fictionsMutex.withLock { fictions }
        } else {
            nonFictionsMutex.withLock {
                nonFictions = books
            }
            nonFictionsMutex.withLock { nonFictions }
        }
    }

    fun findWeeklyBook(title: String): NYTimesBook {
        return nonFictions.firstOrNull { it.title == title } ?: fictions.first { it.title == title }
    }

    fun firebaseAuthWithGoogle(idToken: String, activity: Activity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    user = Firebase.auth.currentUser
                    loginStatus.value = LoginStatus.Login
                    Timber.d("Google Sign in succeed !!!")
                } else {
                    loginStatus.value = LoginStatus.Error
                    Timber.e("Failed to sign in!!!")
                }
            }
    }
}

enum class LoginStatus {
    Login, Logout, Logging, Error
}
