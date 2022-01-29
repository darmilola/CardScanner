package com.me.cardscanner

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserEntity::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}