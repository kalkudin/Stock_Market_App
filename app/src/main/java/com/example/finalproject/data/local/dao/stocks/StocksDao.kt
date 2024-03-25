package com.example.finalproject.data.local.dao.stocks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.finalproject.data.local.model.stocks.StocksEntity

@Dao
interface StocksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStocks(plant: StocksEntity)

    @Delete
    suspend fun deleteStocks(plant: StocksEntity)
}