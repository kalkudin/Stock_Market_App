package com.example.finalproject.di

import android.content.Context
import androidx.room.Room
import com.example.finalproject.data.local.dao.stocks.StocksDao
import com.example.finalproject.data.local.dao.user.UserDao
import com.example.finalproject.data.local.database.AppDatabase
import com.example.finalproject.data.local.database.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "stocks-database"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideStocksDao(appDatabase: AppDatabase): StocksDao {
        return appDatabase.stocksDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}