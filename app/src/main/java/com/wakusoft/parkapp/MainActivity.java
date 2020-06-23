package com.wakusoft.parkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wakusoft.parkapp.ui.login.LoginActivity;
import com.wakusoft.parkapp.ui.menu.MenuActivity;

public class MainActivity extends AppCompatActivity {


    private View.OnClickListener goLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*//FIRE BASE escribir en database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            myRef.child("child 1").setValue("I am the child");*/

            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        try{
            //Guardar datos en sharedPreferences SESSION
            SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
            String email = prefs.getString("NIT", "");

            if(!email.equals("")){
                Intent intent = new Intent(getApplication(), MenuActivity.class);
                startActivityForResult(intent, 0);
            }
        }
        catch(Exception exc){
            System.out.println("Error");
        }



        Button bt = (Button) findViewById(R.id.bt_inicio);
        bt.setOnClickListener(goLogin);



        /*User user = new User("user1234", "abc", "xyz", "First", "January", "2001");

        //Getting Instance of Firebase realtime database
        FirebaseDatabase databaseInstance = FirebaseDatabase.getInstance();

        //Getting Reference to a User node, (it will be created if not already there)
        DatabaseReference userNode = database.getReference("User");

        //Writing the User class object to that reference
        userNode.setValue(user);*/

        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Getting Reference to Root Node
        DatabaseReference myRef = database.getReference();

        //Getting reference to "child 1" node
        myRef = myRef.child("child 1");*/

        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Getting the string value of that node
                String value =  dataSnapshot.getValue(String.class);

                Toast.makeText(MainActivity.this, "Data Received: " + value, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });*/


    }


}
