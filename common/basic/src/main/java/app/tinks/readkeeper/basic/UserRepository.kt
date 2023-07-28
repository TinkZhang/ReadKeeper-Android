package app.tinks.readkeeper.basic

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import app.tinks.readkeeper.basic.model.Book
import app.tinks.readkeeper.basic.model.NYBookType
import app.tinks.readkeeper.basic.model.NYTimesBook
import app.tinks.readkeeper.basic.model.Record
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import timber.log.Timber

object UserRepository {
    val user
        get() = Firebase.auth.currentUser
    val loginStatus: MutableLiveData<LoginStatus> by lazy {
        MutableLiveData<LoginStatus>()
    }

    val isLogged: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val fictionsMutex = Mutex()
    private var fictions: List<NYTimesBook> = listOf()
    private val nonFictionsMutex = Mutex()
    private var nonFictions: List<NYTimesBook> = listOf()

    val nytCollectionRef = Firebase.firestore.collection("nytBestSellers")

    init {
        if (user == null) {
            loginStatus.value = LoginStatus.Logout
            isLogged.value = false
        } else {
            loginStatus.value = LoginStatus.Login
            isLogged.value = true
        }
    }

    private val userDocumentRef = user?.let {
        Timber.d("Signed in user with id: ${user!!.uid}")
        Firebase.firestore.document("user/${user!!.uid}")
    }

    private val bookRef = userDocumentRef?.collection("books")
    private val recordRef = userDocumentRef?.collection("records")

    fun add(book: Book) {
        bookRef?.document(book.basicInfo.uuid)?.set(book)
    }

    fun add(books: List<Book>) {
        books.forEach { add(it) }
    }

    suspend fun getFirstBook(): Book? =
        bookRef?.orderBy(FieldPath.of("timeInfo", "editedTime"))?.limitToLast(1)?.get()?.await()
            ?.toObjects(Book::class.java)?.firstOrNull().also {
                Timber.d("The first book in Firebase is ${it?.basicInfo?.title} ${it?.platform}")
            }

    suspend fun getAllBooks(): List<Book> =
        bookRef?.get()?.await()?.toObjects(Book::class.java) ?: emptyList()

    fun add(record: Record) {
        recordRef?.document(record.id)?.set(record)
    }

    fun update(book: Book) {
        remove(book.basicInfo.uuid)
        add(book.copy(timeInfo = book.timeInfo.copy(editedTime = Timestamp.now())))
    }

    fun removeRecord(id: String) {
        recordRef?.document(id)?.delete()
    }

    fun updateRecord(record: Record) {
        recordRef?.document(record.id)?.apply {
            delete()
            set(record)
        }
    }

    fun remove(uuid: String) {
        bookRef?.document(uuid)?.delete()
    }

    fun signOutWithGoogle() {
        Firebase.auth.signOut()
        loginStatus.value = LoginStatus.Logout
        isLogged.value = false
    }

    fun signInWithGoogle(context: Context) {
        loginStatus.value = LoginStatus.Logging
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.google_sign_in_token_id)).requestEmail()
            .requestProfile().build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)

        val signInIntent = googleSignInClient.signInIntent
        (context as Activity).startActivityForResult(signInIntent, 9001)
    }

    suspend fun getWeeklyBooks(type: NYBookType): List<NYTimesBook> {
        val books = Firebase.firestore.collection(type.name).orderBy("rank").get().await()
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
        Firebase.auth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                loginStatus.value = LoginStatus.Login
                isLogged.value = true
                Timber.d("Google Sign in succeed !!!")
            } else {
                loginStatus.value = LoginStatus.Error
                isLogged.value = false
                Timber.e("Failed to sign in!!!")
            }
        }
    }
}

enum class LoginStatus {
    Login, Logout, Logging, Error
}
