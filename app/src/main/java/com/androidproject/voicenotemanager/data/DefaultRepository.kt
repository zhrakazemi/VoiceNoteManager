package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import kotlinx.coroutines.withContext
import java.util.UUID


/**
 * @param localDataSource - The local data source
 */
class DefaultRepository(
    private val localDataSource: DAO,
) {
    fun createnote(name: String, categoryId: String): String {

        val noteId = UUID.randomUUID().toString()
        val temp = ""
        val note = Note(
            id = noteId,
            name = name,
            recordedVoice = temp,
            userNotes = temp,
            categoryId = categoryId
        )
        localDataSource.upsertNote(note.toLocal())
        return noteId
    }


    fun createCategory(name: String): String {

        val categoryId = UUID.randomUUID().toString()
        val category = Category(
            id = categoryId,
            name = name,
        )
        localDataSource.upsertCategory(category.toLocal())
        return categoryId
    }


    fun updatenote(noteId: String, name: String, categoryId: String , recordedVoice : String , userNote : String) {
        val note = Note(
            id = localDataSource.getNote(noteId).toString(),
            name = name,
            recordedVoice = recordedVoice,
            userNotes = userNote,
            categoryId = categoryId
        )
         ?: throw Exception("Task (id $noteId) not found")

        localDataSource.upsertNote(note.toLocal())
    }


    fun getNotes(categoryId: String): List<Note> {
        return localDataSource.getCategory(categoryId).toExternal()
    }


    fun getNote(noteId : String): Note {
        return localDataSource.getNote(noteId).toExternal()
    }


    fun getCategories(): List<Category> {
        return localDataSource.getCategoryList().toExternal()
    }


}

