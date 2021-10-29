package models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import repository.MessageRepository

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()
    val messageLiveData: LiveData<List<Message>> = repository.messageLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }

    fun add(message: Message) {
        repository.add(message)
    }
}