package com.androidproject.voicenotemanager.ui.record

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidproject.voicenotemanager.DestinationsArgs
import com.androidproject.voicenotemanager.ForegroundService
import com.androidproject.voicenotemanager.VOSKApi
import com.androidproject.voicenotemanager.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RecordUiState(
    val noteName: String = "",
    val recordedText: String = "",
    val timer: Long = 0L,
)

var otimer: Long = 0
var orunning: Boolean = true

/**
 * ViewModel responsible for managing the UI state of a note recording screen.
 *
 * This ViewModel leverages Hilt for dependency injection and utilizes StateFlow
 * to expose the UI state in a reactive way. It interacts with a `VOSKApi` instance for
 * voice recognition, a `Repository` to access and update note data, and a
 * `ForegroundService` (not shown) to handle background recording functionality.
 * It manages the recording process, displays the transcribed text, and allows sharing
 * of the recorded content.
 */
@HiltViewModel
class RecordViewModel @Inject constructor(
    /**
     * Application context obtained through dependency injection.
     */
    @ApplicationContext private val appContext: Context,
    /**
     * Instance of the VOSKApi for voice recognition functionality.
     */
    val voskApi: VOSKApi,
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
     * A StateFlow representing the current UI state, including the transcribed text,
     * the note's name, a timer displaying recording duration, and a flag indicating
     * whether recording is paused. This allows for reactive updates in the UI.
     */
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()

    /**
     * The ID of the note being edited, retrieved from navigation arguments.
     */
    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID_ARG]!!

    init {

        /**
         * Upon initialization, fetch the note's details and start a timer loop.
         */
        getNote()
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (!voskApi.getPause) {
                    _uiState.update { currentState ->
                        currentState.copy(timer = _uiState.value.timer + 1)
                    }
                    otimer = _uiState.value.timer
                    orunning = !voskApi.getPause
                }
            }
        }
    }

    private fun getNote() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(recordedText = repository.getNote(noteId).recordedVoice)
            }
            _uiState.update { currentState ->
                currentState.copy(noteName = repository.getNote(noteId).name)
            }
        }
    }

    private var firstStart = true

    /**
     * start recording and interacts with the foreground service.
     */
    private fun startRecording() {
        if (firstStart) {
            /**
             * Initialize VOSK model on first start only. Consider moving this
             * to a separate method for better organization.
             */
            firstStart = false
            voskApi.recognizeMicrophone()
        }
        viewModelScope.launch {
            // Start foreground service for background recording
            Intent(appContext, ForegroundService::class.java).also {
                it.action = ForegroundService.Actions.START.toString()
                appContext.startService(it)
            }
            delay(500)
            while (!voskApi.getPause) {
                delay(1000)
                viewModelScope.launch {
                    var usernote = repository.getNote(noteId)
                    usernote = usernote.copy(recordedVoice = voskApi.mainText)
                    repository.updateNote(
                        noteId, usernote.name, usernote.categoryId,
                        usernote.recordedVoice, usernote.userNotes
                    )
                }
                _uiState.update { currentState ->
                    currentState.copy(recordedText = voskApi.mainText)
                }
            }
        }
    }

    /**
     * Stops recording and interacts with the foreground service.
     */
    fun stopRecording() {
        Intent(appContext, ForegroundService::class.java).also {
            it.action = ForegroundService.Actions.STOP.toString()
            appContext.startService(it)
        }
    }

    /**
     * start or stop recording.
     */
    fun startStopRecording() {
        if (voskApi.model == null)
            return
        if (voskApi.getPause) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    /**
     * Creates an intent to share the note's content and launches the share dialog.
     */
    fun share(){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, _uiState.value.recordedText)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(shareIntent)
    }
}
