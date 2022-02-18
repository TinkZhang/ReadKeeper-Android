package com.github.tinkzhang.readkeeper.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.github.tinkzhang.basic.UserRepository
import com.github.tinkzhang.homepage.weeklybook.db.WeeklyBookDatabase
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