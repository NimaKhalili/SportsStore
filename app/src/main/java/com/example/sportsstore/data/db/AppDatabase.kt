package com.example.sportsstore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sportsstore.data.Product
import com.example.sportsstore.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDap():ProductLocalDataSource
}