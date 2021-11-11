package repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log

class AuthRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userInfoData: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()
    val loggedOutData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val errorMessage: MutableLiveData<String> = MutableLiveData<String>()

    init {
        userInfoData.value = FirebaseAuth.getInstance().currentUser
    }


    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                userInfoData.value = FirebaseAuth.getInstance().currentUser
                loggedOutData.value = false
            }
            else {
                errorMessage.value = task.exception?.message
                loggedOutData.value = true
            }

        }

    }

    fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task ->
            if (task.isSuccessful){
                userInfoData.value = FirebaseAuth.getInstance().currentUser
                loggedOutData.value = false
            }
            else{
                errorMessage.value = task.exception?.message
                loggedOutData.value = true
            }
        }

    }

    fun signOut() {
        auth.signOut()
        loggedOutData.value = true

    }
}