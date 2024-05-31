package com.androidproject.voicenotemanager.ui.note

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidproject.voicenotemanager.DestinationsArgs
import com.androidproject.voicenotemanager.VOSKApi
import com.androidproject.voicenotemanager.data.Repository
import com.androidproject.voicenotemanager.ui.record.RecordUiState
import com.androidproject.voicenotemanager.ui.record.orunning
import com.androidproject.voicenotemanager.ui.record.otimer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiState(
    var userNote: String = "",
    val noteName: String = "",
    val timer: Long = 0
)

/**
 * ViewModel responsible for managing the UI state of a note editing screen.
 *
 * This ViewModel leverages Hilt for dependency injection and utilizes StateFlow
 * to expose the UI state in a reactive way. It interacts with a Repository to
 * fetch, update, and share a note identified by its ID. Additionally, it manages
 * a timer displayed within the UI.
 */
@HiltViewModel
class NoteViewModel @Inject constructor(
    /**
     * Application context obtained through dependency injection.
     */
    @ApplicationContext private val appContext: Context,
    /**
     * Repository instance used for interacting with note data.
     */
    private val repository: Repository,
    /**
     * SavedStateHandle instance used to retrieve the note ID from navigation arguments.
     */
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    /**
     * A StateFlow representing the current UI state, including the note's name,
     * user-entered content, and a timer value. This allows for reactive updates
     * in the UI.
     */
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /**
     * The ID of the note being edited, retrieved from navigation arguments.
     */
    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID_ARG]!!

    init {
        /**
         * Upon initialization, fetch the note's details and start the timer.
         */
        getNote()
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(timer = otimer)
            }
            while (true) {
                delay(1000)
                if (orunning) {
                    _uiState.update { currentState ->
                        currentState.copy(timer = otimer)
                    }
                }
            }
        }
    }

    /**
     * Fetches the note's details from the repository and updates the UI state
     * with the retrieved name and user notes.
     */
    private fun getNote() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(userNote = repository.getNote(noteId).userNotes)
            }
            _uiState.update { currentState ->
                currentState.copy(noteName = repository.getNote(noteId).name)
            }
        }
    }

    /**
     * Saves the updated note content to the repository. This method retrieves
     * the current note data, updates the user notes field with the latest value
     * from the UI state, and then calls the repository's update method.
     */
    fun saveNote() {
        viewModelScope.launch {
            var usernote = repository.getNote(noteId)
            usernote = usernote.copy(userNotes = _uiState.value.userNote)
            Log.d("TAG", "saveNote: ${usernote.userNotes}")
            repository.updateNote(
                noteId, usernote.name, usernote.categoryId,
                usernote.recordedVoice, usernote.userNotes
            )
        }
    }

    /**
     * Updates the user-entered note content within the UI state.
     *
     * @param newText The new text to be set as the user notes.
     */
    fun updateText(newText: String) {
        _uiState.update {
            it.copy(userNote = newText)
        }
    }

    /**
     * Creates an intent to share the note's content and launches the share dialog.
     */
    fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, _uiState.value.userNote)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(shareIntent)
    }
}