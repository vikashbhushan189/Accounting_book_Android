package vikashbhushan.example.accountingbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    EditText EmailID;
    Button resetBtn;
    ProgressBar mProgressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        EmailID = findViewById(R.id.Email);
        mProgressBar = findViewById(R.id.progressBar);
        resetBtn = findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(EmailID.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetPassword.this, "resetPassword Email sent to your Email ID", Toast.LENGTH_LONG).show();
                            mProgressBar.setVisibility(View.GONE);
                            ResetPassword.this.finish();
                        } else {
                            Toast.makeText(ResetPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
