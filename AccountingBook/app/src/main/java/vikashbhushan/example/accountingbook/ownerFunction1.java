package vikashbhushan.example.accountingbook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ownerFunction1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArtistsAdapter adapter;
    private List<vikashbhushan.example.accountingbook.Artist> artistList;
    Button searchFilter;
    EditText from, to;

    DatabaseReference dbArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_function1);

        recyclerView = findViewById(R.id.recyclerView);
        searchFilter = findViewById(R.id.filter);
        from = findViewById(R.id.fromDate);
        to = findViewById(R.id.toDate);
        dbArtists = FirebaseDatabase.getInstance().getReference("Accountantuser");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artistList = new ArrayList<>();
        adapter = new ArtistsAdapter(this, artistList);
        recyclerView.setAdapter(adapter);
        String Fdate=from.getText().toString();
        String Tdate=to.getText().toString();

        if (TextUtils.isEmpty(Fdate)) {
            from.setError("From Date is required.");

        }
        if (TextUtils.isEmpty(Tdate)) {
            to.setError("To Date is Required.");

        }

        searchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x=from.getText().toString();
                String y=to.getText().toString();
                int val = Integer.parseInt(x);
                int val1 = Integer.parseInt(y);

                    Query query5 = FirebaseDatabase.getInstance().getReference("Accountantuser")
                            .orderByChild("Date").startAt(val)
                            .endAt(val1);
                    query5.addListenerForSingleValueEvent(valueEventListener);

            }
        });
        //5. SELECT * FROM Artists WHERE age < 30
        Query query6 = FirebaseDatabase.getInstance().getReference("Accountantuser");
        query6.addListenerForSingleValueEvent(valueEventListener);


        //6. SELECT * FROM Artists WHERE name = "A%"
//        Query query6 = FirebaseDatabase.getInstance().getReference("Accountantuser")
//                .orderByChild("name")
//                .startAt("A")
//                .endAt("A\uf8ff");

    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            artistList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Artist artist = snapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
