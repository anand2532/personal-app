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
                    val errorCode = response.code()
                    
                    // Parse error message for better user experience
                    val errorMessage = when (errorCode) {
                        401 -> "Invalid API key. Please check your OpenAI API key in local.properties"
                        429 -> "API quota exceeded. Please check your OpenAI billing and plan details.\nVisit: https://platform.openai.com/account/billing"
                        500 -> "OpenAI server error. Please try again later."
                        else -> "API Error ($errorCode): $errorBody"
                    }
                    _errorMessage.value = errorMessage
                }
            } catch (e: Exception) {
                _errorMessage.value = "Network Error: ${e.message}\nPlease check your internet connection."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

