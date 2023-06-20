package com.example.rickopediaapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickopediaapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _message: MutableStateFlow<String?> = MutableStateFlow(null)
    val message = _message.asStateFlow()

    private val _characters = repository.charactersPager()
    val characters = _characters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    ).cachedIn(viewModelScope)

    private fun showSnackbar(message: String?) {
        _message.value = message
    }

    fun snackbarMessageShown() {
        _message.value = null
    }
}