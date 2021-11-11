package repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import models.Comment
import models.Message
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessageRepository {
    private val url = "https://anbo-restmessages.azurewebsites.net/api/"


    private val messageService: MessageService
    val messageLiveData: MutableLiveData<List<Message>> = MutableLiveData<List<Message>>()
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData<List<Comment>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData<String>()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData<String>()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        messageService = build.create(MessageService::class.java)
        getPosts()
    }

    fun getPosts() {
        messageService.getAllMessage().enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if (response.isSuccessful) {
                    messageLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("BANAN", message)
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("BANAN", t.message!!)
            }
        })
    }
    fun add(message: Message) {
        messageService.saveMessage(message).enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful) {
                    Log.d("BANAN", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("BANAN", message)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("BANAN", t.message!!)
            }
        })
    }
    fun delete(id: Int){
        messageService.deleteMessage(id).enqueue(object : Callback<Message>{
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                if (response.isSuccessful){
                    Log.d("BANAN","Deleted: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
            }
        })
    }
    fun getComments(messageId: Int){
        messageService.getComments(messageId).enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful){
                    commentsLiveData.postValue(response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("BANAN",message)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("BANAN",t.message!!)
            }

        })
    }
    fun deleteComments(messageId: Int, commentId: Int){
        messageService.deleteComment(messageId, commentId).enqueue(object : Callback<Comment>{
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful){
                    Log.d("BANAN", "Deleted: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.message())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("BANAN",message)
                }
            }
            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("BANAN",t.message!!)
            }

        })
    }
    fun postComment(messageId: Int, comment: Comment){
        messageService.postComment(messageId, comment).enqueue(object : Callback<Comment>{
            override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                if (response.isSuccessful){
                    Log.d("BANAN", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + " " + response.message())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("BANAN",message)
                }
            }

            override fun onFailure(call: Call<Comment>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("BANAN",t.message!!)
            }
        })
    }


}