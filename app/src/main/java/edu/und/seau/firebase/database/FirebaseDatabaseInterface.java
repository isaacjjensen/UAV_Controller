package edu.und.seau.firebase.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.util.Consumer;

import com.google.firebase.firestore.ListenerRegistration;

import edu.und.seau.firebase.models.server.ServerSettings;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.user.User;

public interface FirebaseDatabaseInterface {

    void createUser(String id, String name, String email);

    void getProfile(String id, Consumer<User> onResult);


    void getUAVFromID(String uavID, Consumer<UAV> onResult);
    void sendCommand(String userKey, String uavKey, Map<String, Object> commandData);

    ListenerRegistration ListenForResponse(String userKey, String uavKey, Consumer<Map<String, Object>> OnResult);
}
