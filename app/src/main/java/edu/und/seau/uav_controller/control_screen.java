package edu.und.seau.uav_controller;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class control_screen  extends AppCompatActivity {

    // Button and Text Field Vars
    private Button btn_start;
    private Button btn_land;
    private Button btn_left;
    private Button btn_right;
    private Button btn_up;
    private Button btn_down;
    private Button btn_for;
    private Button btn_back;
    private Button btn_fl;
    private Button btn_fr;
    private Button btn_bl;
    private Button btn_br;

    //private EditText input_msg;
    private TextView chat_conversation;

    private String user_name,UAV_name;
    private DatabaseReference root ;
    private String temp_key;

    // GPS Vars
    public String[] gps_array = {"","","",""};
    public static long time = 0;
    public static double latitude = 0;
    public static double longitude = 0;
    public static double altitude = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.control_screen);

        // Initialize Button and Text Vars
        chat_conversation = findViewById(R.id.textView);
        btn_start = findViewById(R.id.btn_start);
        btn_land = findViewById(R.id.btn_stop);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        btn_up = findViewById(R.id.btn_up);
        btn_down = findViewById(R.id.btn_down);
        btn_for = findViewById(R.id.btn_for);
        btn_back = findViewById(R.id.btn_back);
        btn_fl = findViewById(R.id.btn_fl);
        btn_fr = findViewById(R.id.btn_fr);
        btn_bl = findViewById(R.id.btn_bl);
        btn_br = findViewById(R.id.btn_br);

        user_name = getIntent().getExtras().get("user_name").toString();
        UAV_name = getIntent().getExtras().get("UAV_name").toString();
        setTitle(" UAV Control: "+user_name);

        root = FirebaseDatabase.getInstance().getReference().child(UAV_name);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","OO"); //Start
                message_root.updateChildren(map2);
            }
        });
        btn_land.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","PP"); //STOP
                message_root.updateChildren(map2);
            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","rr"); // left 25 %
                message_root.updateChildren(map2);
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","vv"); // right 50%
                message_root.updateChildren(map2);
            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","aa"); // up 25%
                message_root.updateChildren(map2);
            }
        });

        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","ee"); // down 25%
                message_root.updateChildren(map2);
            }
        });

        btn_for.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","jj"); // forward 50%
                message_root.updateChildren(map2);
            }


        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","nn"); // backward 50%
                message_root.updateChildren(map2);
            }


        });

        btn_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","zz"); // forward-left 50%
                message_root.updateChildren(map2);
            }


        });

        btn_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","DD"); // forward-right 50%
                message_root.updateChildren(map2);
            }


        });

        btn_bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","HH"); // backward-left 50%
                message_root.updateChildren(map2);
            }


        });

        btn_br.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String,Object> map2 = new HashMap<String, Object>();
                map2.put("name",user_name);
                map2.put("msg","LL"); // backward-right 50%
                message_root.updateChildren(map2);
            }


        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversation(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private String GPS_Coordinates,UAV_Pilot_name;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            GPS_Coordinates = (String) ((DataSnapshot)i.next()).getValue();
            UAV_Pilot_name = (String) ((DataSnapshot)i.next()).getValue();
        }
        chat_conversation.setText("");
        chat_conversation.append(UAV_Pilot_name +" : "+GPS_Coordinates +" \n");
        if(UAV_Pilot_name.equals("REMOTE")){
            gps_array = GPS_Coordinates.split(" ", 4);
            time = Long.parseLong(gps_array[0]);
            latitude = Double.parseDouble(gps_array[1]);
            longitude = Double.parseDouble(gps_array[2]);
            altitude = Double.parseDouble(gps_array[3]);
            Log.d("D:", "Lat: "+latitude+"Long: "+longitude+"Alt: "+altitude+"Time: "+time);
        }
    }
}
