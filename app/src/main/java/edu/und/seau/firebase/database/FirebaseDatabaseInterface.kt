package edu.und.seau.firebase.database

import edu.und.seau.firebase.models.server.ServerSettings
import edu.und.seau.firebase.models.uav.UAV
import edu.und.seau.firebase.models.user.User

interface FirebaseDatabaseInterface {
    fun createUser(id: String, name: String, email: String)

    fun getProfile(id: String, onResult: (User) -> Unit)

    fun listenForUAVs(onResult: (UAV) -> Unit)

    fun getServerSettings(onResult: (ServerSettings) -> Unit)
}