package com.androidproject.voicenotemanager.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The Room Database that contains the Task table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [LocalNote::class ,
                     LocalCategory::class],
                    version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun Dao(): DAO
}