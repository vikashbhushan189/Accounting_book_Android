package vikashbhushan.example.accountingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class zoomImage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);

        Button update=findViewById(R.id.updateDC);

        final ImageView view = findViewById(R.id.displayImageView);

        FirebaseAuth fAuth;
        fAuth=FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = fAuth.getCurrentUser();
        final String EID = firebaseUser.getEmail();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Accountantuser").child(EID.replace(".", ",")).child("image");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Picasso.get().load(value).into(view);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateDelete = new Intent(zoomImage.this, updateddelete.class);
                updateDelete.putExtra("id", EID);
                startActivity(updateDelete);
            }
        });

    }
}
