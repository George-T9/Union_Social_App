package com.example.union.view.mainScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.union.CoroutineDispatcherProvider
import com.example.union.datasource.PostSource
import com.example.union.model.MainModel
import com.example.union.model.PostModel
import com.example.union.datasource.repository.MainRepository
import com.example.union.model.PostList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
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

    init {
        fetchUserDetails()
    }

    fun getPostPagination() : Flow<PagingData<PostList>> {
        return Pager(PagingConfig(pageSize = 10)){
            PostSource(mainRepository = mainRepository)
        }.flow
    }

    private fun fetchUserDetails() {
        _mainUiState.value = MainUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val userResponse = mainRepository.getMainData()
                _mainUiState.value =
                    MainUiState.Loaded(userData = userResponse)
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

    private fun onQueryException(e: Exception) {
        MainUiState.Error(message = "Error = $e")
    }

    private fun onQueryLimitReached(e: HttpException) {
        MainUiState.Error(message = "Error = ${e.message()}")
    }


    sealed class MainUiState() {
        object Empty : MainUiState()
        object Loading : MainUiState()
        class Loaded(val userData : MainModel) : MainUiState()
        class Error(val message: String) : MainUiState()
    }
}

