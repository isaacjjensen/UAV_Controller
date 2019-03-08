package edu.und.seau.firebase.models.uav

import com.google.firebase.*
import com.google.firebase.firestore.*

data class UAVResponse(val id: String,
                       val Name: String,
                       val Location: GeoPoint,
                       val LocationTimeStamp: Timestamp,
                       val CommunicationTimestamp: Timestamp)

fun UAVResponse.isValid() = id.isNotBlank()
        && Name.isNotBlank()

fun UAVResponse.mapToUAV() = UAV(id,Name,Location,LocationTimeStamp,CommunicationTimestamp)

data class UAV(val id: String,
               val Name: String,
               val Location: GeoPoint,
               val LocationTimeStamp: Timestamp,
               val CommunicationTimestamp: Timestamp)