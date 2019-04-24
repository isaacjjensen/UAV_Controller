package edu.und.seau.firebase.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.core.util.Consumer;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.ListenerRegistration;

import org.jetbrains.annotations.NotNull;

import edu.und.seau.firebase.models.server.ServerSettings;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.user.User;

public interface FirebaseDatabaseInterface {

    void createUser(String id, String name, String email);

    void getProfile(String id, Consumer<User> onResult);


    void getUAVFromID(String uavID, String userID, Consumer<UAV> onResult);
    void DeleteResponse(@NotNull String userKey, @NotNull String uavID, Consumer<DocumentSnapshot> task);
    void SendRequest(@NotNull String userKey, @NotNull String uavKey, @NotNull Map<String, Object> request);

    ListenerRegistration ListenForResponse(@NotNull String userKey, @NotNull String uavKey, Consumer<Map<String, Object>> OnResult);
}
