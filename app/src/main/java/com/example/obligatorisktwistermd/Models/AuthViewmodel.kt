package com.example.obligatorisktwistermd.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.obligatorisktwistermd.Repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewmodel : ViewModel() {
    private val repository = AuthRepository()
    val userInfoData: LiveData<FirebaseUser> = repository.userInfoData

    fun signIn(email: String, password: String){
        repository.signIn(email, password)
    }

    fun createUser(email: String, password: String){
        repository.createUser(email, password)
    }




}