package models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import repository.MessageRepository

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()
    val messageLiveData: MutableLiveData<List<Message>> = repository.messageLiveData
    val commentsLiveData: MutableLiveData<List<Comment>> = repository.commentsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getPosts()
    }
    operator fun get(index: Int): Message? {
        return messageLiveData.value?.get(index)
    }

    fun add(message: Message) {
        repository.add(message)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun getComments(messageId: Int){
        repository.getComments(messageId)
    }

    fun deleteComments(messageId: Int,commentId: Int){
        repository.deleteComments(messageId,commentId)
    }

    fun addComment(messageId: Int, comment: Comment){
        repository.postComment(messageId, comment)
    }
}