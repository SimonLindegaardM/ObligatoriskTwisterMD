package repository

import models.Message
import retrofit2.Call
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    fun getAllMessage(): Call<List<Message>>

//    @GET("messages/{bookId}")
//    fun getBookById(@Path("bookId") bookId: Int): Call<Message>
//
    @POST("messages")
    fun saveMessage(@Body message: Message): Call<Message>
//
//    @DELETE("books/{id}")
//    fun deleteBook(@Path("id") id: Int): Call<Message>
//
//    @PUT("books/{id}")
//    fun updateBook(@Path("id") id: Int, @Body book: Message): Call<Message>
}