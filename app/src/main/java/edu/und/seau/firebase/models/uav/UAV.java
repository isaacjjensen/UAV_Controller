package edu.und.seau.firebase.models.uav;

import androidx.annotation.NonNull;
import edu.und.seau.common.UAVCommon;

public class UAV {

    private static UAV _instance;

    private String id = "";
    private String Name = "";

    public String getId() {
        return id;
    }

    public String getName(){
        return Name;
    }

    public void setName(String value) { Name = value;}

    public void setId(String id) {this.id = id;}

    public void generateNewID()
    {
        id = UAVCommon.getRandomString();
    }

    public UAV()
    {

    }

    public UAV(String id)
    {
        this.id = id;
    }

    public Boolean isValid()
    {
        return ((!id.isEmpty()) && (!Name.isEmpty()));
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: "+Name+ "ID: "+ id;
    }

    public static UAV getInstance(){
        if (_instance == null){
            _instance = new UAV();
        }
        return _instance;
    }

}
