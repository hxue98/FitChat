package fitchat.com.fitchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        final EditText userNameOrEmail = findViewById(R.id.signin_email_or_username);
        final EditText password = findViewById(R.id.signin_password);

        findViewById(R.id.signin_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userNameOrEmail.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(SigninActivity.this, "Please enter both fields", Toast.LENGTH_SHORT).show();
                } else if (userNameOrEmail.getText().toString().contains("@")) {
                    for (User user : Model.getInstance().getAccounts()) {
                        if (userNameOrEmail.getText().toString().equals(user.getEmail()) &&
                                password.getText().toString().equals(user.getPassword())) {
                            startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
                        }
                    }
                    Toast.makeText(SigninActivity.this, "Wrong username/email or password", Toast.LENGTH_SHORT).show();
                } else {
                    for (User user : Model.getInstance().getAccounts()) {
                        if (userNameOrEmail.getText().toString().equals(user.getUsername()) &&
                                password.getText().toString().equals(user.getPassword())) {
                            startActivity(new Intent(SigninActivity.this, DashboardActivity.class));
                        }
                    }
                    Toast.makeText(SigninActivity.this, "Wrong username/email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
