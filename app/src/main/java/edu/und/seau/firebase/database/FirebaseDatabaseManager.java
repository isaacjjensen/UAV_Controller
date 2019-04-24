package edu.und.seau.firebase.database;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;

import androidx.core.util.Consumer;

import edu.und.seau.firebase.commands.CommandManager;
import edu.und.seau.firebase.modelmapper.FirebaseUAVMapper;
import edu.und.seau.firebase.modelmapper.FirebaseUserMapper;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.user.User;

public class FirebaseDatabaseManager implements FirebaseDatabaseInterface{
    private static final String KEY_USER = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_UAV  = "UAV";
    private static final String KEY_SERVERSETTINGS = "ServerSettings";
    private static final String KEY_REQUESTS = "Requests";
    private static final String KEY_RESPONSES = "Responses";

    private FirebaseFirestore database;


    @Inject
    public FirebaseDatabaseManager(FirebaseFirestore database){
        this.database = database;
    }

    @Override
    public void createUser(@NotNull String id, @NotNull String name, @NotNull String email) {
        User user = new User(id, name, email);
        DocumentReference docRef = database.collection(KEY_USER).document(id);
        docRef.get().addOnCompleteListener(command -> {
           if(!Objects.requireNonNull(command.getResult()).exists()){
               docRef.set(FirebaseUserMapper.getUserHash(user));
           }
        });
    }

    @Override
    public void getProfile(String id, Consumer<User> onResult) {
        DocumentReference docRef =database
                .collection(KEY_USER)
                .document(id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if(Objects.requireNonNull(document).exists()) {
                    onResult.accept(FirebaseUserMapper.getUserFromHash(document.getData()));
                }
                else {
                    onResult.accept(null);
                }
            }
        });
    }

    public void getUAVFromID(String uavID, String userID, Consumer<UAV> onResult){
        DocumentReference documentReference = database
                .collection(KEY_UAV)
                .document(uavID);

        documentReference.get().addOnCompleteListener(task -> {
            UAV result = null;
            if(task.isSuccessful()){
                result = FirebaseUAVMapper.getUAVFromHash(Objects.requireNonNull(task.getResult()).getData());
            }
            onResult.accept(result);
        });
    }

    public void SendRequest(@NotNull String userKey, @NotNull String uavKey, @NotNull Map<String, Object> request){
        database.collection(KEY_UAV)
                .document(uavKey)
                .collection(KEY_REQUESTS)
                .document(userKey)
                .set(request);
    }

    public ListenerRegistration ListenForResponse(@NotNull String userKey, @NotNull String uavKey, Consumer<Map<String, Object>> OnResult){
        return database.collection(KEY_UAV)
                .document(uavKey)
                .collection(KEY_RESPONSES).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if(queryDocumentSnapshots != null){
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                            if(documentSnapshot.getId().equals(userKey)){
                                HashMap<String, Object> responseData = new HashMap<>(documentSnapshot.getData());
                                responseData.put(KEY_ID, userKey);
                                OnResult.accept(responseData);
                            }
                        }
                    }
                });
    }

    public void DeleteResponse(@NotNull String userKey, @NotNull String uavID, Consumer<DocumentSnapshot> task){
        database.collection(KEY_UAV)
                .document(uavID)
                .collection(KEY_RESPONSES)
                .document(userKey).delete();
    }
}
