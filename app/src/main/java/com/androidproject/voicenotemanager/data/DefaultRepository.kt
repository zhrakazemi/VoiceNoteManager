package com.androidproject.voicenotemanager.data

import androidx.room.Dao
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Singleton class implementing the `Repository` interface for data access.
 *
 * This class leverages Hilt for dependency injection and utilizes a local data source
 * (likely a Room DAO) to interact with the database. It provides methods for creating,
 * updating, fetching, and deleting notes and categories.
 */
@Singleton
class DefaultRepository @Inject constructor(
    /**
     * Injected instance of the DAO for accessing the local data source.
     */
    private val localDataSource: DAO,
): Repository {

    /**
     * Creates a new note in the database.
     *
     * @param name The name of the note.
     * @param categoryId The ID of the category the note belongs to.
     * @return The ID of the newly created note.
     * @throws Exception If there is an error creating the note.
     */
    override suspend fun createNote(name: String, categoryId: String): String {

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

    /**
     * Creates a new category in the database.
     *
     * @param name The name of the category.
     * @return The ID of the newly created category.
     * @throws Exception If there is an error creating the category.
     */
    override suspend fun createCategory(name: String): String {

        val categoryId = UUID.randomUUID().toString()
        val category = Category(
            id = categoryId,
            name = name,
        )
        localDataSource.upsertCategory(category.toLocal())
        return categoryId
    }

    /**
     * Updates an existing note in the database.
     *
     * @param noteId The ID of the note to update.
     * @param name The updated name of the note.
     * @param categoryId The updated category ID for the note.
     * @param recordedVoice The transcribed voice content of the note.
     * @param userNote The user's additional notes for the recording.
     * @throws Exception If there is an error updating the note.
     */
    override suspend fun updateNote(noteId: String, name: String, categoryId: String , recordedVoice : String , userNote : String) {
        val note = Note(
            id = noteId,
            name = name,
            recordedVoice = recordedVoice,
            userNotes = userNote,
            categoryId = categoryId
        )

        localDataSource.upsertNote(note.toLocal())
    }

    /**
     * Retrieves all notes belonging to a specific category from the database.
     *
     * @param categoryId The ID of the category to fetch notes from.
     * @return A list of `Note` objects representing the retrieved notes.
     * @throws Exception If there is an error fetching notes.
     */
    override suspend fun getNotes(categoryId: String): List<Note> {
        return localDataSource.getCategory(categoryId).toExternal()
    }

    /**
     * Retrieves a specific note by its ID from the database.
     *
     * @param noteId The ID of the note to retrieve.
     * @return A `Note` object representing the retrieved note.
     * @throws Exception If there is an error fetching the note.
     */
    override suspend fun getNote(noteId : String): Note {
        return localDataSource.getNote(noteId).toExternal()
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return A list of `Category` objects representing the retrieved categories.
     * @throws Exception If there is an error fetching categories.
     */
    override suspend fun getCategories(): List<Category> {
        return localDataSource.getCategoryList().toExternal()
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return A list of `Category` objects representing the retrieved categories.
     * @throws Exception If there is an error fetching categories.
     */
    override suspend fun getCategoryName(categoryId: String): String {
        return localDataSource.getCategoryName(categoryId)
    }

}

