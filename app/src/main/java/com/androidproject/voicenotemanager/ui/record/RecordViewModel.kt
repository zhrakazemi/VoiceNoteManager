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


@HiltViewModel
class RecordViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    val voskApi: VOSKApi,
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()
    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID_ARG]!!

    init {
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

    private fun startRecording() {
        if (firstStart) {
            firstStart = false
            voskApi.recognizeMicrophone()
        }
        viewModelScope.launch {
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

    fun stopRecording() {
        Intent(appContext, ForegroundService::class.java).also {
            it.action = ForegroundService.Actions.STOP.toString()
            appContext.startService(it)
        }
    }

    fun startStopRecording() {
        if (voskApi.model == null)
            return
        if (voskApi.getPause) {
            startRecording()
        } else {
            stopRecording()
        }
    }

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
