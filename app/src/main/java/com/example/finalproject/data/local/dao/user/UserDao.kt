package com.example.finalproject.data.local.dao.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.data.local.model.stocks.StocksEntity
import com.example.finalproject.data.local.model.user.UserIdEntity
import com.example.finalproject.data.local.model.user_stocks_cross_ref.UserStocksCrossRefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserIdEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserStocksCrossRef(crossRef: UserStocksCrossRefEntity)

    @Delete
    suspend fun deleteUserStocksCrossRef(crossRef: UserStocksCrossRefEntity)

    @Query("SELECT * FROM stocksentity INNER JOIN userstockscrossrefentity ON stocksentity.symbol = userstockscrossrefentity.stocksSymbol WHERE userstockscrossrefentity.userId = :userId")
    fun getWatchlistedStocksForUser(userId: String): Flow<List<StocksEntity>>
}