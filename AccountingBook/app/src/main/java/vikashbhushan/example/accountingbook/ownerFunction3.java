package vikashbhushan.example.accountingbook;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ownerFunction3 extends AppCompatActivity {
    ListView l;
    DatabaseReference databaseReference;
    ArrayList<String> arrList = new ArrayList<>();
    ArrayAdapter<String> arrAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_function3);
        databaseReference = FirebaseDatabase.getInstance().getReference("Accountantuser");
        l = findViewById(R.id.listUser);
        arrAdp = new ArrayAdapter<>(this, R.layout.activity_user_content, arrList);
        l.setAdapter(arrAdp);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(userContent.class).toString();
                arrList.add(value);
                arrAdp.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
