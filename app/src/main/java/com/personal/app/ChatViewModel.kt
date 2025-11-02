package com.personal.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _chatResponse = MutableLiveData<String?>()
    val chatResponse: LiveData<String?> = _chatResponse

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val chatService = ChatService.create()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = chatService.sendMessage(ChatRequest(
                    messages = listOf(
                        ChatMessage("user", message)
                    )
                ))
                if (response.isSuccessful && response.body() != null) {
                    val chatResponse = response.body()!!
                    _chatResponse.value = chatResponse.choices.firstOrNull()?.message?.content 
                        ?: "No response from assistant"
                } else {
                    val errorBody = response.errorBody()?.string() ?: response.message()
                    _errorMessage.value = "Error: $errorBody"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

