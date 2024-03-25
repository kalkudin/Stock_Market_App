package com.example.finalproject.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create the new table with the correct schema
        database.execSQL("""
            CREATE TABLE new_StocksEntity (
                symbol TEXT NOT NULL,
                companyName TEXT NOT NULL,
                description TEXT NOT NULL,
                image TEXT NOT NULL,
                price REAL,
                changes REAL,
                PRIMARY KEY(symbol)
            )
        """)
        database.execSQL("""
            INSERT INTO new_StocksEntity (symbol, companyName, description, image, price, changes)
            SELECT symbol, companyName, description, image, price, changes FROM StocksEntity
        """)
        database.execSQL("DROP TABLE StocksEntity")

        database.execSQL("ALTER TABLE new_StocksEntity RENAME TO StocksEntity")
    }
}