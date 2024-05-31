package com.androidproject.voicenotemanager.data

interface Repository {
    suspend fun createNote(name: String, categoryId: String): String

    suspend fun createCategory(name: String): String

    suspend fun updateNote(noteId: String, name: String, categoryId: String , recordedVoice : String , userNote : String)

    suspend fun getNotes(categoryId: String): List<Note>

    suspend fun getNote(noteId : String): Note

    suspend fun getCategories(): List<Category>

    suspend fun getCategoryName(categoryId: String): String
}