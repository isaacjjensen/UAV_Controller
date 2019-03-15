package edu.und.seau.uav_controller;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class ControlCommand {
    public float x;
    public float y;
    public float z;

    public ControlCommand() {
        // Default constructor required
    }

    public ControlCommand(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("x", x);
        result.put("y", y);
        result.put("z", z);

        return result;
    }
}
