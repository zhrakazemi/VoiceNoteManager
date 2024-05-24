package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @param localDataSource - The local data source
 */
@Singleton
class DefaultRepository @Inject constructor(
    private val localDataSource: DAO,
): Repository {
    override fun createNote(name: String, categoryId: String): String {

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


    override fun createCategory(name: String): String {

        val categoryId = UUID.randomUUID().toString()
        val category = Category(
            id = categoryId,
            name = name,
        )
        localDataSource.upsertCategory(category.toLocal())
        return categoryId
    }


    override fun updateNote(noteId: String, name: String, categoryId: String , recordedVoice : String , userNote : String) {
        val note = Note(
            id = localDataSource.getNote(noteId).toString(),
            name = name,
            recordedVoice = recordedVoice,
            userNotes = userNote,
            categoryId = categoryId
        )

        localDataSource.upsertNote(note.toLocal())
    }


    override fun getNotes(categoryId: String): List<Note> {
        return localDataSource.getCategory(categoryId).toExternal()
    }


    override fun getNote(noteId : String): Note {
        return localDataSource.getNote(noteId).toExternal()
    }


    override fun getCategories(): List<Category> {
        return localDataSource.getCategoryList().toExternal()
    }

    override fun getCategoryName(categoryId: String): String {
        return localDataSource.getCategoryName(categoryId)
    }


}

