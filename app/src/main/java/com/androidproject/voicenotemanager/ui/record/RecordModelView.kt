package com.androidproject.voicenotemanager.ui.record

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidproject.voicenotemanager.DestinationsArgs
import com.androidproject.voicenotemanager.data.Repository
import com.androidproject.voicenotemanager.ui.notelist.NoteListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RecordUiState(
    val recordedText: String = "",
    val time: String = ""
) {
}

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecordUiState())
    val uiState: StateFlow<RecordUiState> = _uiState.asStateFlow()
    private val noteId: String = savedStateHandle[DestinationsArgs.NOTE_ID_ARG]!!


    init {
        getNote()

    }

    private fun getNote() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(recordedText = repository.getNote(noteId).recordedVoice)
            }
        }
    }

}
