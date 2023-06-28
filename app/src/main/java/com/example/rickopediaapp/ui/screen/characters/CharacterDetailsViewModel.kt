package com.example.rickopediaapp.ui.screen.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickopediaapp.data.Repository
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.navigation.CharacterArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: Repository
) : ViewModel() {
    companion object {
        private const val TAG = "CharacterDetailsViewModel"
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
