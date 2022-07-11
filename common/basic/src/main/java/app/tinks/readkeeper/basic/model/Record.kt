package app.tinks.readkeeper.basic.model

import com.google.firebase.Timestamp
import java.util.*

data class Record(
    val id: String = UUID.randomUUID().toString(),
    val uuid: String = UUID.randomUUID().toString(),
    val startPage: Int = 0,
    val endPage: Int,
    val note: String? = null,
    val timestamp: Timestamp = Timestamp.now()
)
