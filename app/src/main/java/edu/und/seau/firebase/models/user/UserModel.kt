package edu.und.seau.firebase.models.user

data class  UserResponse(val id: String,
                         val username: String,
                         val email: String)

fun UserResponse.isValid() = id.isNotBlank()
        && username.isNotBlank()
        && email.isNotBlank()

fun UserResponse.mapToUser() = User(id,username,email)

data class User(val id: String,
                val username: String,
                val email: String)