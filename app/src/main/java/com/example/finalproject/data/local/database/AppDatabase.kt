package com.example.finalproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalproject.data.local.dao.stocks.StocksDao
import com.example.finalproject.data.local.dao.user.UserDao
import com.example.finalproject.data.local.model.stocks.StocksEntity
import com.example.finalproject.data.local.model.user.UserIdEntity
import com.example.finalproject.data.local.model.user_stocks_cross_ref.UserStocksCrossRefEntity

@Database(entities = [StocksEntity::class, UserIdEntity::class, UserStocksCrossRefEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stocksDao(): StocksDao
    abstract fun userDao(): UserDao
}