package com.example.union.view.chatScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.union.CoroutineDispatcherProvider
import com.example.union.model.ChatModel
import com.example.union.datasource.repository.ChatRepository
import com.example.union.model.FriendsList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Empty)
    val uiState: StateFlow<ChatUiState> = _uiState
    private var _chatlist : List<FriendsList> = listOf()
    private val _showingChatList = MutableLiveData<MutableList<FriendsList>>()
    val showingChatList get() = _showingChatList

    init {
        fetchChatList()
    }

    private fun fetchChatList() {
        _uiState.value = ChatUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {

                val response = chatRepository.getAllChat()
                _uiState.value = ChatUiState.Loaded(data = response)
                _chatlist = response.FriendList
                withContext(coroutineDispatcherProvider.Main()){
                    searchFriendChat()
                }

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


    fun searchFriendChat(query: String = ""): LiveData<MutableList<FriendsList>> {
        if (query.isEmpty()){
            if (_chatlist.isNotEmpty()){
                _showingChatList.value = _chatlist as MutableList<FriendsList>
            }
        }else {
           _showingChatList.value = _chatlist.filter { friendsList -> friendsList.Name.contains(query) } as MutableList<FriendsList>
        }
        return _showingChatList
    }


    private fun onQueryException(e: Exception) {

        _uiState.value = ChatUiState.Error(message = e.message.toString())
    }

    private fun onQueryLimitReached(e: HttpException) {
        _uiState.value = ChatUiState.Error(message = e.message.toString())
    }

    sealed class ChatUiState() {
        object Empty : ChatUiState()
        object Loading : ChatUiState()
        class Loaded(val data: ChatModel) : ChatUiState()
        class Error(val message: String) : ChatUiState()
    }
}