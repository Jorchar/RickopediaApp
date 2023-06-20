package com.example.rickopediaapp.ui.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickopediaapp.CharacterArgs
import com.example.rickopediaapp.data.Repository
import com.example.rickopediaapp.data.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val characterArgs: CharacterArgs = CharacterArgs(savedStateHandle)

    private val _character: MutableStateFlow<Character> =
        MutableStateFlow(repository.getCharacterById(characterArgs.characterId))
    val character = _character.asStateFlow()

    private val _episodes = repository.episodePager(character.value.episodesList)
    val episodes = _episodes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PagingData.empty()
    ).cachedIn(viewModelScope)
}
