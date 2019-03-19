package edu.und.seau.firebase.database;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import edu.und.seau.common.FirebaseConstants;
import edu.und.seau.firebase.modelmapper.FirebaseUserMapper;
import edu.und.seau.firebase.models.server.ServerSettings;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.user.User;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static edu.und.seau.common.FirebaseConstants.KEY_NAME;

public class FirebaseDatabaseManager implements FirebaseDatabaseInterface{
    private static final String KEY_USER = "User";
    private static final String KEY_ID = "id";
    private static final String KEY_UAV  = "UAV";
    private static final String KEY_SERVERSETTINGS = "ServerSettings";

    private FirebaseFirestore database;


    @Inject
    public FirebaseDatabaseManager(FirebaseFirestore database){
        this.database = database;
    }

    @Override
    public void createUser(@NotNull String id, @NotNull String name, @NotNull String email) {
        User user = new User(id, name, email);

        database.collection(KEY_USER).add(FirebaseUserMapper.getUserHash(user));
    }

    @Override
    public void getProfile(String id, Consumer<User> onResult) {

        DocumentReference docRef =database
                .collection(KEY_USER)
                .document(id);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot document = task.getResult();
                if(Objects.requireNonNull(document).exists())
                {
                    onResult.accept(FirebaseUserMapper.getUserFromHash(document.getData()));
                }
            }
        });
    }

    public void getUAVList(Consumer<ArrayList<UAV>> onResult){
        database.collection(KEY_UAV).get().addOnCompleteListener(command -> {
           if(command.isSuccessful())
           {
               onResult.accept(getUAVListFromDB(Objects.requireNonNull(command.getResult())));
           }
        });
    }

    private ArrayList<UAV> getUAVListFromDB(QuerySnapshot snapshot)
    {
        ArrayList<UAV> returnList = new ArrayList<>();
        for (DocumentSnapshot documentSnapshot:snapshot.getDocuments()) {
            UAV uavToAdd = documentSnapshotToUAV(documentSnapshot);
            if(uavToAdd != null)
            {
                returnList.add(uavToAdd);
            }
        }
        return returnList;
    }

    private UAV documentSnapshotToUAV(DocumentSnapshot snapshot)
    {
        UAV returnValue = null;
        if(snapshot.contains(KEY_ID) && snapshot.contains(KEY_NAME))
        {

            String id = (String)snapshot.get(FirebaseConstants.KEY_ID);
            String name = (String) snapshot.get(KEY_NAME);
            returnValue = new UAV(id);
            returnValue.setName(name);
        }
        return  returnValue;
    }


    @Override
    public void listenForUAVs(Consumer<UAV> onResult) {
    }

    @Override
    public void getServerSettings(Consumer<ServerSettings> onResult) {

    }

}
