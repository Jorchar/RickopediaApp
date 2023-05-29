package com.example.rickopediaapp.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickopediaapp.data.Repository
import com.example.rickopediaapp.data.Result
import com.example.rickopediaapp.data.model.Character
import com.example.rickopediaapp.data.model.previewCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository, val hello: String) :
    ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _character: MutableStateFlow<Character> =
        MutableStateFlow(previewCharacter.copy(name = "Fetch character"))
    val character = _character.asStateFlow()
    private val _text: MutableStateFlow<String> = MutableStateFlow("")
    val text = _text.asStateFlow()
    private val _message: MutableStateFlow<String?> = MutableStateFlow(null)
    val message = _message.asStateFlow()
    private fun showSnackbar(message: String?) {
        _message.value = message
    }

    fun snackbarMessageShown() {
        _message.value = null
    }

    fun onFieldValueChange(value: String) {
        Log.e(TAG, "text: $value")
        _text.value = value
    }

    fun fetchCharacter() {
        viewModelScope.launch {
            when (val result = repository.getCharacterById(_text.value.toInt())) {
                is Result.Success -> {
                    _character.value = result.data
                }

                is Result.Error -> {
                    showSnackbar(result.exception?.localizedMessage)
                }

                Result.Loading -> Unit
            }
        }
    }
}