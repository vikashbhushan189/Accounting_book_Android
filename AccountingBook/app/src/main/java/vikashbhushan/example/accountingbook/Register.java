package vikashbhushan.example.accountingbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends AppCompatActivity {
    ImageButton ownerButton, accountantButton;
    // TextView ownerView, accountantView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ownerButton = findViewById(R.id.ownerBtn);
        accountantButton = findViewById(R.id.accountantBtn);
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        ownerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (firebaseUser==null) {
                 startActivity(new Intent(getApplicationContext(), ownerLogin.class));
             }else {
                 startActivity(new Intent(getApplicationContext(), ownerFunctons.class));
             }
            }
        });
        accountantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firebaseUser==null) {
                    startActivity(new Intent(getApplicationContext(), AccountantLogin.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), accountantFunctions.class));
                }            }
        });
    }

}
