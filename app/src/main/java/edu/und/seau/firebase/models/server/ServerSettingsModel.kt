package edu.und.seau.firebase.models.server

import com.google.firebase.firestore.model.value.IntegerValue


data class ServerSettingsResponse(val UAVTimeout: IntegerValue)

fun ServerSettingsResponse.mapToServerSettings() = ServerSettings(UAVTimeout)

data class ServerSettings(val UAVTimeout: IntegerValue)