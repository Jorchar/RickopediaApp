package com.example.rickopediaapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.codelab.android.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(private val dataStore: DataStore<UserPreferences>) {

    private val TAG: String = "UserPreferenceRepository"

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }
}