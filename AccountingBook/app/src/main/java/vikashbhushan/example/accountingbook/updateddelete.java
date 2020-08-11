package vikashbhushan.example.accountingbook;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateddelete extends AppCompatActivity {
    EditText Debit, Credit;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateddelete);

        String key = getIntent().getExtras().get("id").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("Accountantuser").child(key.replace(".", ","));

        Debit = findViewById(R.id.updateDebit);
        Credit = findViewById(R.id.updateCredit);
        Debit.setText(getIntent().getStringExtra("debit"));
        Credit.setText(getIntent().getStringExtra("credit"));

    }


//    public void btnDelete_click(View view) {
//        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    ref.removeValue();
//                    Toast.makeText(updateddelete.this, "Record Deleted", Toast.LENGTH_SHORT).show();
//                    updateddelete.this.finish();
//                } else {
//                    Toast.makeText(updateddelete.this, "Task not completed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    public void btnUpdate_click(View view) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (Debit.getText().toString().trim().isEmpty() && Credit.getText().toString().trim().isEmpty()){
                    Toast.makeText(updateddelete.this, "No change is Done", Toast.LENGTH_SHORT).show();
                    updateddelete.this.finish();
                }else {
                    dataSnapshot.getRef().child("debit").setValue(Debit.getText().toString());
                    dataSnapshot.getRef().child("credit").setValue(Credit.getText().toString());
                    Toast.makeText(updateddelete.this, "Data Update Succesfully", Toast.LENGTH_SHORT).show();
                    updateddelete.this.finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}