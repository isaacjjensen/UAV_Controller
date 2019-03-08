package edu.und.seau.firebase.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import java.net.PasswordAuthentication
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(private val authentication: FirebaseAuth) : FirebaseAuthenticationInterface {
    override fun register(username: String, password: String, email: String, onResult: (Boolean) -> Unit) {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isComplete && it.isSuccessful){
                authentication.currentUser?.updateProfile(UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(username)
                        .build())

                onResult(true)
            } else {
                onResult(false)
            }
        }
    }
    override fun getUserID(): String = authentication.currentUser?.uid ?:""
    override fun getUserName(): String = authentication.currentUser?.displayName ?:""

    override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
       authentication.signInWithEmailAndPassword(email,password).addOnCompleteListener{
           onResult(it.isComplete && it.isSuccessful)
       }
    }

    override fun logOut(onResult: () -> Unit) {
       authentication.signOut()
        onResult()
    }
}