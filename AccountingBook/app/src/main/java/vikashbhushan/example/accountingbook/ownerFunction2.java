package vikashbhushan.example.accountingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ownerFunction2 extends AppCompatActivity {

    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegister;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_function2);

        mFullName = findViewById(R.id.userName);
        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        mPhone = findViewById(R.id.userphone);
        mRegister = findViewById(R.id.userRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();
                final String debit = "0";
                final String credit = "0";
                final String image = "0";
                final String timestamp = "1";


                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setText("Password is Required.");
                }
                if (password.length() < 6) {
                    mPassword.setError("password must be >=6 characters");
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    mEmail.setError("FullName is required.");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    mEmail.setError("Phone is required.");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(ownerFunction2.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//
                        if (task.isSuccessful()) {

                            userID = firebaseAuth.getCurrentUser().getEmail().replace(".", ",");

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Accountantuser").child(userID);
                            // DocumentReference databaseReference1=fStore.collection("users").document(userID);
                            Map<String, Object> xyz = new HashMap<>();
                            xyz.put("name", fullName);
                            xyz.put("email", email);
                            xyz.put("phone", phone);
                            xyz.put("password", password);
                            xyz.put("image", image);
                            xyz.put("timestamp", timestamp);
                            xyz.put("id", email);
                            xyz.put("debit", debit);
                            xyz.put("credit", credit);


                            //databaseReference1.set(xyz);
                            databaseReference.setValue(xyz)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(ownerFunction2.this, "Registration Done..", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), ownerFunctons.class));
                                        }
                                    });
                        } else {
                            Toast.makeText(ownerFunction2.this, "Error uploading", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }

}
