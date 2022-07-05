package app.github.tinkzhang.homepage.weeklybook.db

import androidx.room.Database
import androidx.room.RoomDatabase
import app.tinks.readkeeper.basic.model.NYTimesBook

@Database(entities = [NYTimesBook::class], version = 1)
abstract class WeeklyBookDatabase : RoomDatabase() {
    abstract fun weeklyBookDao(): WeeklyBookDao
}