package edu.und.seau.firebase.commands;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.core.util.Consumer;

import org.jetbrains.annotations.NotNull;

import edu.und.seau.common.FirebaseConstants;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;

public class CommandManager {
    private static final String KEY_COMMAND_NUMBER = "COMMAND NUMBER";
    private static final String KEY_ROLL = "ROLL";
    private static final String KEY_PITCH = "PITCH";
    private static final String KEY_YAW = "YAW";
    private static final String KEY_LIFT = "LIFT";
    private static final String KEY_DURATION = "DURATION";
    private static final String KEY_GPS_LOCATION = "GPS LOCATION";
    private static final String KEY_CONTROL_STATUS = "CONTROL STATUS";

    private FirebaseDatabaseInterface databaseInterface;

    @Inject
    public CommandManager(FirebaseDatabaseInterface firebaseDatabaseInterface){
        databaseInterface = firebaseDatabaseInterface;
    }


    private void SendCommand(@NotNull String userKey, @NotNull String uavKey, @NotNull HashMap<String, Object> request){
        request.put(FirebaseConstants.KEY_TIME_STAMP, Timestamp.now());
        databaseInterface.DeleteResponse(userKey,uavKey,null);
        databaseInterface.SendRequest(userKey, uavKey, request);
    }

    public ListenerRegistration ListenForResponse(String userKey, String uavKey, Consumer<Map<String,Object>> OnResponse){
        return databaseInterface.ListenForResponse(userKey,uavKey,OnResponse);
    }

    public void SendCommand0(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(0));
    }
    public void SendCommand1(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1));
    }

    public void SendCommand2(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(2));
    }

    public void SendCommand3(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(3));
    }

    public void SendCommand1000(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1000));
    }

    public void SendCommand1001(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1001));
    }

    public void SendCommand1002(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1002));
    }

    public void SendCommand1003(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1003));
    }

    public void SendCommand10000(String userKey ,String uavKey){
        SendCommand(userKey, uavKey,  CreateCommandNumberHashMap(10000));
    }

    public void SendCommand10001(String userKey ,String uavKey,Float Roll, Float Pitch, Float Yaw, Float Lift, int Duration)
    {
        HashMap<String, Object> Command10001Data = CreateCommandNumberHashMap(10001);
        Command10001Data.put(KEY_ROLL,Roll);
        Command10001Data.put(KEY_PITCH,Pitch);
        Command10001Data.put(KEY_YAW,Yaw);
        Command10001Data.put(KEY_LIFT,Lift);
        Command10001Data.put(KEY_DURATION,Duration);
        SendCommand(userKey, uavKey, Command10001Data);
    }

    public void  SendCommand10002(String userKey ,String uavKey,GeoPoint GPSPoint){
        HashMap<String,Object> Command10002Data = CreateCommandNumberHashMap(10002);
        Command10002Data.put(KEY_GPS_LOCATION,GPSPoint);
        SendCommand(userKey, uavKey, Command10002Data);
    }

    public void SendCommand100000(String userKey ,String uavKey){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(100000));
    }

    private HashMap<String, Object> CreateCommandNumberHashMap(int CommandNumber){
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put(KEY_COMMAND_NUMBER,CommandNumber);
        return returnValue;
    }



}
