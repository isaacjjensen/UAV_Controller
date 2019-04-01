package edu.und.seau.firebase.modelmapper;

import com.google.firebase.Timestamp;

import java.util.Map;

import edu.und.seau.common.FirebaseConstants;
import edu.und.seau.firebase.models.uav.UAV;
import edu.und.seau.firebase.models.uav.UavDBModel;

public class FirebaseUAVMapper {

    private static final String KEY_NAME = FirebaseConstants.KEY_NAME;
    private static final String KEY_ID = FirebaseConstants.KEY_ID;

    public static UAV getUAVFromHash(Map<String, Object> hashMap){
        UAV uav = null;
        if(hashMap != null){
            String id = (String)hashMap.getOrDefault(KEY_ID,"");
            String name = (String)hashMap.getOrDefault(KEY_NAME,"");
            uav = new UAV(id);
            uav.setName(name);
        }
        return uav;
    }
}
