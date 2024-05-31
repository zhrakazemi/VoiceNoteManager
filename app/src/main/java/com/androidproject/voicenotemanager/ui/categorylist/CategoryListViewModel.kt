package com.androidproject.voicenotemanager.ui.categorylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidproject.voicenotemanager.data.Category
import com.androidproject.voicenotemanager.data.DefaultRepository
import com.androidproject.voicenotemanager.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryListUiState(
    val categories: List<Category> = emptyList(),
    val noteCount: MutableMap<String, Int> = mutableMapOf()
)

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoryListUiState())
    val uiState: StateFlow<CategoryListUiState> = _uiState.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(categories = repository.getCategories())
            }
            val noteCount: MutableMap<String, Int> = mutableMapOf()
            for (category in uiState.value.categories) {
                val count = repository.getNotes(category.id)
                noteCount[category.id] = count.size
            }
            _uiState.update { currentState ->
                currentState.copy(noteCount = noteCount)
            }
        }
    }

    fun createCategory(name: String) {
        viewModelScope.launch {
            repository.createCategory(name)
            getCategories()
        }
    }
}