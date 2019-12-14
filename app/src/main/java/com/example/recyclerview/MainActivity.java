package com.example.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //List<service> service1;
    List<Query> queryList;
    String currentlyActivePage;
    FirebaseDatabase database;
    DatabaseReference reff , SellRef , HireRef , RentRef , OthersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//ArrayList That contains the list of services avaiable to be sold....this has to be filled by database
        //service1 = new ArrayList<>();
        queryList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        SellRef = database.getReference("Sell");
        HireRef = database.getReference("Hire");
        RentRef = database.getReference("Rent");
        OthersRef = database.getReference("Others");

        currentlyActivePage = "Sell";
        reff = SellRef;

        //queryList.add(new Query("Temp 1" , 9999 , "Katil " , "Abhishek" , "97555" , "1575950165906.jpg"));
        //queryList.add(new Query("Temp 2" , 9999 , "Katil " , "Abhishek" , "97555" , "1575950165906.jpg"));


        final RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,queryList);
        RecyclerView myrv = (RecyclerView) findViewById(R.id.recycler_view);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                queryList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Query queryObject= new Query();
                    queryObject = ds.getValue(Query.class);
                    Log.i("Info" , queryObject.getQuery_name());
                    queryList.add(new Query(queryObject.getQuery_name() , queryObject.getQuery_price() , queryObject.getQuery_desc() ,queryObject.getQuery_creator() , queryObject.getQuery_contact(),queryObject.getQuery_image_id()));
                    myAdapter.notifyDataSetChanged();
                    Log.i("Info", "Fine till here");
                    Log.i("Size of List", String.valueOf(queryList.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Info" , "Event Listener Closed");
            }
        });


    }
}
