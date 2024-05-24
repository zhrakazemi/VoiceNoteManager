package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DAO {

    @Query("SELECT * FROM note WHERE id =:id")
    suspend fun getNote(id: String): LocalNote

    @Query("SELECT * FROM note WHERE categoryId =:id")
    suspend fun getCategory(id: String): List<LocalNote>

    @Query("SELECT * FROM category")
    suspend fun getCategoryList(): List<LocalCategory>

    @Query("SELECT name FROM category WHERE id =:id")
    suspend fun getCategoryName(id: String): String

    @Upsert
    suspend fun upsertNote(note: LocalNote)

    @Upsert
    suspend fun upsertCategory(category: LocalCategory)

}