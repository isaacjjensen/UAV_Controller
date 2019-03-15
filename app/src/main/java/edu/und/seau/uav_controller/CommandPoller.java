package edu.und.seau.uav_controller;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class CommandPoller implements Runnable {

    private static String TAG = "CommandPoller";

    private DatabaseReference root;
    private String user;
    private boolean polling;
    private long pollTime;
    private float currentX;
    private float currentY;
    private float currentZ;

    public CommandPoller(long pollTime, DatabaseReference root, String user) {
        this.pollTime = pollTime;
        this.root = root;
        this.user = user;
        polling = true;
        currentX = 0.0f;
        currentY = 0.0f;
        currentZ = 0.0f;
    }

    public void setPollTime(long pollTime) {
        this.pollTime = pollTime;
    }

    public long getPollTime() {
        return pollTime;
    }

    // Signals the run method to stop running
    // *** NOTE: joining with the thread after stopping is important
    public void stop() {
        polling = false;
    }

    // Run method of this Runnable class
    public void run() {
        // In case of restarting the same CommandPoller
        if (polling == false) {
            polling = true;
        }
        while (polling) {
            if (commandChanged()) {
                currentX = control_screen.commandX;
                currentY = control_screen.commandY;
                currentZ = control_screen.commandZ;
                writeNewControlCommand(currentX, currentY, currentZ);
            }
            try {
                Thread.sleep(pollTime);
            } catch (Exception e) {
                Log.d(TAG, "Exception while pausing: " + e);
            }
        }
    }

    // Returns true if any of the values have changes since the last poll (reduces redundancy)
    private boolean commandChanged() {
        if ((currentX != control_screen.commandX) || (currentY != control_screen.commandY) ||
                (control_screen.commandZ != 0.0f) || (currentZ != control_screen.commandZ)) {
            return true;
        }
        return false;
    }

    private void writeNewControlCommand(float x, float y, float z) {
        Log.d(TAG, "Writing new command: " + x + " " + y + " " + z);
        Map<String, Object> map = new HashMap<>();
        String key = root.push().getKey();
        root.updateChildren(map);
        DatabaseReference message_root = root.child(key);
        ControlCommand cc = new ControlCommand(x, y, z);
        Map<String, Object> ccValues = cc.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("name", user);
        childUpdates.put("msg", ccValues);

        message_root.updateChildren(childUpdates);
    }
}
