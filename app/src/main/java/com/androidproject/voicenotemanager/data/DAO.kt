package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DAO {

    @Query("SELECT * FROM note WHERE id =:id")
    fun getNote(id: String): LocalNote

    @Query("SELECT * FROM note WHERE categoryId =:id")
    fun getCategory(id: String): List<LocalNote>

    @Query("SELECT * FROM category")
    fun getCategoryList(): List<LocalCategory>

    @Query("SELECT name FROM category WHERE id =:id")
    fun getCategoryName(id: String): String

    @Upsert
     fun upsertNote(note: LocalNote)

    @Upsert
     fun upsertCategory(category: LocalCategory)

}