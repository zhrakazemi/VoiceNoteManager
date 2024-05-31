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


/**
 * ViewModel responsible for managing the UI state of a category list screen.
 *
 * This ViewModel leverages Hilt for dependency injection and utilizes StateFlow
 * to expose the UI state in a reactive way. It interacts with a Repository to
 * fetch and manage categories and their associated note counts.
 */
@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {
    /**
     * A StateFlow representing the current UI state, including the list of categories
     * and a map containing note counts for each category. This allows for reactive
     * updates in the UI.
     */
    private val _uiState = MutableStateFlow(CategoryListUiState())
    val uiState: StateFlow<CategoryListUiState> = _uiState.asStateFlow()

    init {
        /**
         * Upon initialization, fetch categories from the repository and update the UI state.
         */
        getCategories()
    }

    /**
     * Fetches categories from the repository and updates the UI state with the retrieved list.
     * This method also calculates and updates the note count for each category.
     */
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

    /**
     * Creates a new category with the given name. This method delegates the creation
     * to the repository and then refetches categories to ensure the UI reflects the
     * newly created category.
     *
     * @param name The name of the category to be created.
     */
    fun createCategory(name: String) {
        viewModelScope.launch {
            repository.createCategory(name)
            getCategories()
        }
    }
}