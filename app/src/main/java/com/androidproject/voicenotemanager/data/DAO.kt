package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DAO {

    @Query("SELECT * FROM note , attachment WHERE id =:Noteid")
    fun getNote(Noteid: String): LocalNote

    @Query("SELECT * FROM note WHERE categoryId =id")
    fun getCategory(id: String): LocalNote


}