package app.tinks.readkeeper.basic.convertors

import app.tinks.readkeeper.basic.database.RecordEntity
import app.tinks.readkeeper.basic.model.Record
import com.google.firebase.Timestamp

fun Record.convertToRecordEntity() = RecordEntity(
    uuid = this.uuid,
    id = this.id,
    startPage = this.startPage,
    endPage = this.endPage,
    note = this.note,
    timestamp = this.timestamp.seconds
)

fun RecordEntity.convertToRecord() = Record(
    uuid = this.uuid,
    id = this.id,
    startPage = this.startPage,
    endPage = this.endPage,
    note = this.note,
    timestamp = Timestamp(this.timestamp, 0)
)