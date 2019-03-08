package edu.und.seau.firebase.authentication

interface FirebaseAuthenticationInterface {

    fun login(email: String, password: String, onResult: (Boolean) -> Unit)
    fun register(username: String, password: String, email: String, onResult: (Boolean) -> Unit)
    fun getUserID(): String
    fun getUserName(): String
    fun logOut(onResult: () -> Unit)
}