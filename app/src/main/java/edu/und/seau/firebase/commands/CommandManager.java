package edu.und.seau.firebase.commands;

import com.google.firebase.firestore.GeoPoint;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import androidx.core.util.Consumer;
import edu.und.seau.firebase.database.FirebaseDatabaseInterface;

public class CommandManager {
    private static final String KEY_COMMAND_NUMBER = "COMMAND NUMBER";
    private static final String KEY_ROLL = "ROLL";
    private static final String KEY_PITCH = "PITCH";
    private static final String KEY_YAW = "YAW";
    private static final String KEY_LIFT = "LIFT";
    private static final String KEY_DURATION = "DURATION";
    private static final String KEY_GPS_LOCATION = "GPS LOCATION";

    private FirebaseDatabaseInterface databaseInterface;

    @Inject
    public CommandManager(FirebaseDatabaseInterface firebaseDatabaseInterface){
        databaseInterface = firebaseDatabaseInterface;
    }


    public void SendCommand(String userKey ,String uavKey, HashMap<String, Object> request, Consumer<Map<String,Object>> OnResult){
        databaseInterface.ListenForResponse(userKey,uavKey,OnResult);
        databaseInterface.sendCommand(userKey, uavKey, request);
    }

    public void SendCommand0(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(0),OnResult);
    }
    public void SendCommand1(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1), OnResult);
    }

    public void SendCommand2(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(2), OnResult);
    }

    public void SendCommand3(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(3), OnResult);
    }

    public void SendCommand1000(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1000), OnResult);
    }

    public void SendCommand1001(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1001),OnResult);
    }

    public void SendCommand1002(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1002),OnResult);
    }

    public void SendCommand1003(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(1003),OnResult);
    }

    public void SendCommand10000(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey,  CreateCommandNumberHashMap(10000),OnResult);
    }

    public void SendCommand10001(String userKey ,String uavKey,Float Roll, Float Pitch, Float Yaw, Float Lift, int Duration, Consumer<Map<String,Object>> OnResult)
    {
        HashMap<String, Object> Command10001Data = CreateCommandNumberHashMap(10001);
        Command10001Data.put(KEY_ROLL,Roll);
        Command10001Data.put(KEY_PITCH,Pitch);
        Command10001Data.put(KEY_YAW,Yaw);
        Command10001Data.put(KEY_LIFT,Lift);
        Command10001Data.put(KEY_DURATION,Duration);
        SendCommand(userKey, uavKey, Command10001Data,OnResult);
    }

    public void  SendCommand10002(String userKey ,String uavKey,GeoPoint GPSPoint, Consumer<Map<String,Object>> OnResult){
        HashMap<String,Object> Command10002Data = CreateCommandNumberHashMap(10002);
        Command10002Data.put(KEY_GPS_LOCATION,GPSPoint);
        SendCommand(userKey, uavKey, Command10002Data,OnResult);
    }

    public void SendCommand100000(String userKey ,String uavKey,Consumer<Map<String,Object>> OnResult){
        SendCommand(userKey, uavKey, CreateCommandNumberHashMap(100000),OnResult);
    }

    private HashMap<String, Object> CreateCommandNumberHashMap(int CommandNumber){
        HashMap<String, Object> returnValue = new HashMap<>();
        returnValue.put(KEY_COMMAND_NUMBER,CommandNumber);
        return returnValue;
    }



}
