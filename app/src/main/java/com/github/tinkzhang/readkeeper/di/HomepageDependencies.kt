package com.github.tinkzhang.readkeeper.di

import com.github.tinkzhang.homepage.weeklybook.db.WeeklyBookDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HomepageDependencies{

    fun weeklyBookDatabase(): WeeklyBookDatabase
}