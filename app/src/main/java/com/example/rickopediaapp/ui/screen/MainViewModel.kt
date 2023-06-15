package com.example.rickopediaapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickopediaapp.data.Repository
import com.example.rickopediaapp.data.Result
import com.example.rickopediaapp.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CharacterUiState{
    object Loading: CharacterUiState
    data class Success(val character: Character): CharacterUiState
}

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
    ).cachedIn(this.viewModelScope)

    private val _character: MutableStateFlow<CharacterUiState> = MutableStateFlow(CharacterUiState.Loading)

    val character = _character.asStateFlow()

    fun getCharacterById(characterId: Int){
        viewModelScope.launch {
            when(val result = repository.getCharacterById(characterId)){
                is Result.Success -> {
                    _character.value = CharacterUiState.Success(result.data)
                }
                is Result.Error -> {
                    showSnackbar(result.exception?.message)
                }
                is Result.Loading -> {
                    _character.value = CharacterUiState.Loading
                }
            }
        }
    }
    private fun showSnackbar(message: String?) {
        _message.value = message
    }

    fun snackbarMessageShown() {
        _message.value = null
    }
}