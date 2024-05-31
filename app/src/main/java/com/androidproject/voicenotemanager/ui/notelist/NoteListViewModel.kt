package com.androidproject.voicenotemanager.ui.notelist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidproject.voicenotemanager.DestinationsArgs
import com.androidproject.voicenotemanager.data.DefaultRepository
import com.androidproject.voicenotemanager.data.Note
import com.androidproject.voicenotemanager.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteListUiState(
    val notes: List<Note> = emptyList(),
    val categoryName: String = ""
)

/**
 * ViewModel responsible for managing the UI state of a note list screen for a specific category.
 *
 * This ViewModel leverages Hilt for dependency injection and utilizes StateFlow
 * to expose the UI state in a reactive way. It interacts with a Repository to
 * fetch notes and the category name associated with a given category ID
 * retrieved from navigation arguments. It also provides functionality to create new notes
 * within the specified category.
 */

@HiltViewModel
class NoteListViewModel @Inject constructor(
    /**
     * Repository instance used for interacting with note and category data.
     */
    private val repository: Repository,
    /**
     * SavedStateHandle instance used to retrieve the category ID from navigation arguments.
     */
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * A StateFlow representing the current UI state, including the list of notes
     * belonging to a specific category and the category's name. This allows for reactive
     * updates in the UI.
     */
    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()

    /**
     * The ID of the category being displayed, retrieved from navigation arguments.
     */
    private val categoryId: String = savedStateHandle[DestinationsArgs.CATEGORY_ID_ARG]!!

    init {
        /**
         * Upon initialization, fetch the notes and category name for the specified ID.
         */
        getNotes()
    }

    /**
     * Fetches notes for the given category ID from the repository and updates the UI state
     * with the retrieved list. Additionally, retrieves the category name and updates the
     * UI state accordingly.
     */
    private fun getNotes() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(notes = repository.getNotes(categoryId))
            }
            _uiState.update { currentState ->
                currentState.copy(categoryName = repository.getCategoryName(categoryId))
            }
        }
    }

    /**
     * Creates a new note with the provided name within the category identified by `categoryId`.
     * This method interacts with the repository to create the note and then refetches the
     * list of notes to ensure the UI reflects the newly created note.
     *
     * @param name The name of the new note to be created.
     */
    fun createNote(name: String) {
        viewModelScope.launch {
            repository.createNote(name, categoryId)

            _uiState.update { currentState ->
                currentState.copy(notes = repository.getNotes(categoryId))
            }
        }
    }
}