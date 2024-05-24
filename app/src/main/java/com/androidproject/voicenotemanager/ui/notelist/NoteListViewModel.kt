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

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteListUiState())
    val uiState: StateFlow<NoteListUiState> = _uiState.asStateFlow()
    private val categoryId: String = savedStateHandle[DestinationsArgs.CATEGORY_ID_ARG]!!

    init {
        getNotes()
    }

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

    fun createNote(name: String) {
        viewModelScope.launch {
            repository.createNote(name, categoryId)

            _uiState.update { currentState ->
                currentState.copy(notes = repository.getNotes(categoryId))
            }
        }
    }
}