package edu.und.seau.firebase.database

import com.google.firebase.database.*
import  edu.und.seau.firebase.models.*
import edu.und.seau.firebase.models.server.ServerSettings
import edu.und.seau.firebase.models.server.ServerSettingsResponse
import edu.und.seau.firebase.models.server.mapToServerSettings
import edu.und.seau.firebase.models.uav.UAV
import edu.und.seau.firebase.models.uav.UAVResponse
import edu.und.seau.firebase.models.uav.isValid
import edu.und.seau.firebase.models.uav.mapToUAV
import edu.und.seau.firebase.models.user.User
import edu.und.seau.firebase.models.user.UserResponse
import edu.und.seau.firebase.models.user.isValid
import javax.inject.Inject

private const val KEY_USER = "User"
private const val KEY_UAV = "UAV"
private const val KEY_SERVERSETTINGS = "ServerSettings"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id,name,email)

        database.reference.child(KEY_USER).child(id).setValue(user)
    }

    override fun getProfile(id: String, onResult: (User) -> Unit) {
        database.reference
                .child(KEY_USER)
                .child(id)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) = Unit

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(UserResponse::class.java)
                        if(user!!.isValid())
                        {
                            user.run { onResult(User(id,username,email)) }
                        }

                    }
                })
    }

    override fun listenForUAVs(onResult: (UAV) -> Unit) {
        database.reference
                .child(KEY_UAV)
                .addChildEventListener(object  : ChildEventListener{
                    override fun onCancelled(p0: DatabaseError)  = Unit
                    override fun onChildChanged(p0: DataSnapshot, p1: String?) = Unit
                    override fun onChildMoved(p0: DataSnapshot, p1: String?) = Unit
                    override fun onChildRemoved(p0: DataSnapshot) = Unit

                    override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                        snapshot.getValue(UAVResponse::class.java)?.run {
                            if(isValid()) {
                                onResult(mapToUAV())
                            }
                        }
                    }
                })
    }

    override fun getServerSettings(onResult: (ServerSettings) -> Unit)
    {
        database.reference
                .child(KEY_SERVERSETTINGS)
                .addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) = Unit

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(ServerSettingsResponse::class.java)?.run {
                            onResult(mapToServerSettings())
                        }
                    }
                })
    }
}