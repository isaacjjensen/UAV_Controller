package edu.und.seau.firebase.modelmapper;

import java.util.HashMap;
import java.util.Map;

import edu.und.seau.common.FirebaseConstants;
import edu.und.seau.firebase.models.user.User;

public class FirebaseUserMapper {
    private static final String KEY_USERNAME = FirebaseConstants.KEY_USERNAME;
    private static final String KEY_ID = FirebaseConstants.KEY_ID;
    private static final String KEY_EMAIL = FirebaseConstants.KEY_EMAIL;


    public static HashMap<String, Object> getUserHash(User user)
    {
        HashMap<String, Object> hashMap = new HashMap<>();
        if(user.isValid())
        {
            hashMap.put(KEY_ID, user.id);
            hashMap.put(KEY_USERNAME, user.username);
            hashMap.put(KEY_EMAIL, user.email);
        }
        return hashMap;
    }

    public static User getUserFromHash(Map<String, Object> hashMap)
    {
        User returnValue = null;
        if(hashMap != null)
        {
            String id = (String) hashMap.get(KEY_ID);
            String username = (String) hashMap.get(KEY_USERNAME);
            String email = (String) hashMap.get(KEY_EMAIL);
            returnValue = new User(id,username,email);
        }
        return returnValue;
    }

}
