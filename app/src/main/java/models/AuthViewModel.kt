package models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val repository: AuthRepository = AuthRepository()
    val userInfoData: MutableLiveData<FirebaseUser> = repository.userInfoData
    val errorMessage: MutableLiveData<String> = repository.errorMessage
    val loggedOutData: MutableLiveData<Boolean> = repository.loggedOutData



    fun signIn(email: String, password: String){
        repository.signIn(email, password)
        userInfoData.value = repository.userInfoData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }

    fun createUser(email: String, password: String){
        repository.createUser(email, password)
        userInfoData.value = repository.userInfoData.value
        errorMessage.value = repository.errorMessage.value
        loggedOutData.value = repository.loggedOutData.value
    }

    fun logOut(){
        repository.signOut()
        loggedOutData.value = repository.loggedOutData.value
    }




}