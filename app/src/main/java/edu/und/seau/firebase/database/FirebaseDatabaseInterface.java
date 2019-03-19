package edu.und.seau.firebase.database;

import java.util.ArrayList;

import androidx.core.util.Consumer;
import edu.und.seau.firebase.models.server.ServerSettings;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.user.User;

public interface FirebaseDatabaseInterface {

    void createUser(String id, String name, String email);

    void getProfile(String id, Consumer<User> onResult);

    void listenForUAVs(Consumer<UAV> onResult);

    void getServerSettings(Consumer<ServerSettings> onResult);
    void getUAVList(Consumer<ArrayList<UAV>> onResult);
}
