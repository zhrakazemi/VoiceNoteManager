package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DAO {

    @Query("SELECT * FROM note WHERE id =:id")
    fun getNote(id: String): LocalNote

    @Query("SELECT * FROM note WHERE categoryId =:id")
    fun getCategory(id: String): LocalNote

    @Query("SELECT * FROM category")
    fun getCategoryList(): LocalNote

    @Upsert
    suspend fun upsertNote(note: LocalNote)

    @Upsert
    suspend fun upsertCategory(category: LocalCategory)

}