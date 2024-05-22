package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface DAO {

    @Query("SELECT * FROM note , attachment WHERE id =:Noteid")
    fun getNote(Noteid: String): LocalNote

    @Query("SELECT * FROM note WHERE categoryId =:id")
    fun getCategory(id: String): LocalNote

    @Query("SELECT * FROM category  ")
    fun getCategoryList(): LocalNote


    @Query("SELECT * FROM attachment WHERE id =:id ")
    fun getAttachment(id: String): LocalNote

    @Upsert
    suspend fun upsertNote(note: LocalNote)

    @Upsert
    suspend fun upsertCategory(category: LocalCategory)

    @Upsert
    suspend fun upsertattachment(attachment: LocalAttachment)
}