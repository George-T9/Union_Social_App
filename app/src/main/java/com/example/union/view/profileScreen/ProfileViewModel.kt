package com.example.union.view.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.union.CoroutineDispatcherProvider
import com.example.union.model.ProfileModel
import com.example.union.datasource.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
):ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Empty)
    val uiState :StateFlow<ProfileUiState> = _uiState

    init {
        fetchProfileData()
    }

    private fun fetchProfileData(){
        _uiState.value = ProfileUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {

                val response = profileRepository.getProfileData()
                _uiState.value = ProfileUiState.Loaded(data = response)
            }catch (e: Exception) {
                if (e is HttpException && e.code() == 429) {
                    withContext(coroutineDispatcherProvider.Main()) {
                        onQueryLimitReached(e)
                    }
                } else {
                    withContext(coroutineDispatcherProvider.Main()) {
                        onQueryException(e)
                    }
                }
            }
        }
    }

    private fun onQueryException(e: Exception) {
        _uiState.value = ProfileUiState.Error(message = e.message.toString())
    }

    private fun onQueryLimitReached(e: HttpException) {
        _uiState.value = ProfileUiState.Error(message = e.message.toString())
    }


    sealed class ProfileUiState() {
        object Empty : ProfileUiState()
        object Loading : ProfileUiState()
        class Loaded(val data: ProfileModel) : ProfileUiState()
        class Error(val message: String) : ProfileUiState()
    }

}