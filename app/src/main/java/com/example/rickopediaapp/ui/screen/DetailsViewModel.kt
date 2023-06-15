package com.example.rickopediaapp.ui.screen

import androidx.lifecycle.ViewModel
import com.example.rickopediaapp.data.Repository
import com.example.rickopediaapp.data.Result
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _character: MutableStateFlow<Character?> = MutableStateFlow(previewCharacter)
    val character = _character.asStateFlow()

    suspend fun getCharacterById(characterId: Int){
        when(val result = repository.getCharacterById(characterId)){
            is Result.Success -> {
                _character.value = result.data
            }
            is Result.Error -> {
                //TODO go back and show snackbar
                //showSnackbar(result.exception?.message)
            }
            is Result.Loading -> {
                _character.value = null
            }
        }
    }
}
