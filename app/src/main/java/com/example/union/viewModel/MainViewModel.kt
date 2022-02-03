package com.example.union.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.union.CoroutineDispatcherProvider
import com.example.union.model.MainModel
import com.example.union.model.PostModel
import com.example.union.datasource.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    @ApplicationContext private val applicationContext: Context,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Empty)
    val mainUiState: StateFlow<MainUiState> get() = _mainUiState

    private val _postUiState = MutableStateFlow<PostUiState>(PostUiState.Empty)
    val postUiState: StateFlow<PostUiState> get() = _postUiState

    init {
        fetchUserDetails()
        fetchAllPosts()
    }

    private fun fetchUserDetails() {
        _mainUiState.value = MainUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = mainRepository.getMainData()
                _mainUiState.value = MainUiState.Loaded(response)
            } catch (e: Exception) {
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

    private fun fetchAllPosts(){
        _postUiState.value = PostUiState.Loading
        viewModelScope.launch (coroutineDispatcherProvider.IO()){
            try {
                val response = mainRepository.getPostData()
                _postUiState.value = PostUiState.Loaded(response)


            }catch (e:Exception){
                if (e is HttpException && e.code() == 429){
                    onQueryLimitReached(e)
                }else{
                    onQueryException(e)
                }
            }
        }
    }

    private fun onQueryException(e: Exception) {
        MainUiState.Error(message = "Error = $e")
    }

    private fun onQueryLimitReached(e: HttpException) {
        MainUiState.Error(message = "Error = ${e.message()}")
    }


    sealed class MainUiState() {
        object Empty : MainUiState()
        object Loading : MainUiState()
        class Loaded(val data: MainModel) : MainUiState()
        class Error(val message: String) : MainUiState()
    }

    sealed class PostUiState() {
        object Empty : PostUiState()
        object Loading : PostUiState()
        class Loaded(val data: PostModel) : PostUiState()
        class Error(val message: String) : PostUiState()
    }
}