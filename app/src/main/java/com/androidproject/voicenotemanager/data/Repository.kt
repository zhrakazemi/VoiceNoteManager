package com.androidproject.voicenotemanager.data

interface Repository {
    fun createNote(name: String, categoryId: String): String

    fun createCategory(name: String): String

    fun updateNote(noteId: String, name: String, categoryId: String , recordedVoice : String , userNote : String)

    fun getNotes(categoryId: String): List<Note>

    fun getNote(noteId : String): Note

    fun getCategories(): List<Category>

    fun getCategoryName(categoryId: String): String

}