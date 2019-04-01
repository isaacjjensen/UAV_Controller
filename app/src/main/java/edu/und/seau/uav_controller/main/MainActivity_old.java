package edu.und.seau.uav_controller.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import edu.und.seau.uav_controller.R;

public class MainActivity_old extends AppCompatActivity {

    private Button  add_UAV;
    private String UAV_name="UND UAV";

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_UAVs = new ArrayList<>();
    private final String name="HOST";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_UAV = (Button) findViewById(R.id.btn_enter_UAV);
        //listView = (ListView) findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_of_UAVs);

        listView.setAdapter(arrayAdapter);


        add_UAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<>();
                map.put(UAV_name, "");
                root.updateChildren(map);

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_UAVs.clear();
                list_of_UAVs.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), control_screen_old.class);
                intent.putExtra("UAV_name",UAV_name);
                intent.putExtra("user_name",name);
                startActivity(intent);
            }
        });

    }
}
