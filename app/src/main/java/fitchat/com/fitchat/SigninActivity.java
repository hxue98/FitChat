package fitchat.com.fitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity implements OnClickListener {
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private EditText mEmail;
    private EditText mPassword;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mEmail = findViewById(R.id.signin_email_or_username);
        mPassword = findViewById(R.id.signin_password);
        mAuth = FirebaseAuth.getInstance();
        ok = findViewById(R.id.signin_ok);
        ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view == ok) {
            email = mEmail.getText().toString().trim();
            password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }


            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SigninActivity.this, "Sign in Success", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SigninActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(SigninActivity.this, "Sign in Fail", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}
