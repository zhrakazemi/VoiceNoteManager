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
) {

}

@HiltViewModel
class NoteViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID_ARG]!!

    init {
        getNote()
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(timer = otimer)
            }
            while (true){
                delay(1000)
                if (orunning) {
                    _uiState.update { currentState ->
                        currentState.copy(timer = otimer)
                    }
                }
            }
        }
    }

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

    fun updateText(newText: String) {
        _uiState.update {
            it.copy(userNote = newText)
        }
    }

    fun share(){
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