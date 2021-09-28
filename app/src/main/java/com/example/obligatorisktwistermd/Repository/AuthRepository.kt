package com.example.obligatorisktwistermd.Repository

import androidx.lifecycle.MutableLiveData
import com.example.obligatorisktwistermd.Models.User
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository {

    private lateinit var auth: FirebaseAuth
    val userInfoData: MutableLiveData<FirebaseUser> = MutableLiveData()
    val loggedOutData: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    init {
        userInfoData.value = FirebaseAuth.getInstance().currentUser
    }


    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                userInfoData.value = FirebaseAuth.getInstance().currentUser
            }
            else {
                errorMessage.value = task.exception?.message
            }

        }

    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                userInfoData.value = FirebaseAuth.getInstance().currentUser
            }
            else{
                errorMessage.value = task.exception?.message
            }
        }

    }

    fun signOut() {
        auth.signOut()
        loggedOutData.value = true

    }
}