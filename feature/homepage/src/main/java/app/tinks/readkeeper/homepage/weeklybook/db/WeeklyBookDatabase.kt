package app.tinks.readkeeper.homepage.weeklybook.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.tinks.readkeeper.basic.ReadApplication
import app.tinks.readkeeper.basic.model.NYTimesBook

val weeklyBookDatabase: WeeklyBookDatabase = Room.databaseBuilder(
    ReadApplication.getContext(),
    WeeklyBookDatabase::class.java,
    "weekly_book_database"
).build()

@Database(entities = [NYTimesBook::class], version = 1)
abstract class WeeklyBookDatabase : RoomDatabase() {
    abstract fun weeklyBookDao(): WeeklyBookDao
}