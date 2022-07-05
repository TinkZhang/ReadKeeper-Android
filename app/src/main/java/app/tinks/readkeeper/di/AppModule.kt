package app.tinks.readkeeper.readkeeper.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import app.tinks.readkeeper.homepage.weeklybook.db.WeeklyBookDatabase
import app.tinks.readkeeper.basic.DataStoreRepository
import app.tinks.readkeeper.basic.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository = UserRepository

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ): DataStoreRepository = DataStoreRepository(context)

    @Provides
    @Singleton
    fun provideWeeklyBookDatabase(app: Application): WeeklyBookDatabase =
        Room.databaseBuilder(app, WeeklyBookDatabase::class.java, "weekly_book_database")
            .build()

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile("data") }
        )

}