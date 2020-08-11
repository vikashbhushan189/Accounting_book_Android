package vikashbhushan.example.accountingbook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountantLogin extends AppCompatActivity {
    public static final String TAG = "tag";
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView forgotPassword;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant_login);
        forgotPassword = findViewById(R.id.forgotPass);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.resetBtn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                final Intent intent = new Intent(AccountantLogin.this, accountantFunctions.class);
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be >=6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authentication

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(AccountantLogin.this, "logged in sucessfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), accountantFunctions.class));
                            startActivity(intent);
                            progressBar.setVisibility(View.GONE);
                            AccountantLogin.this.finish();
                        } else {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(AccountantLogin.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            AccountantLogin.this.finish();
                        }
                    }
                });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPassword.class));
            }
        });
    }
}
