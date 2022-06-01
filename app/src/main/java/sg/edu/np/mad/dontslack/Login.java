package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    private final String TAG = "Login Activity";
    private static String etUsername;
    private static String etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /* Login Button */
        Button myLoginButton = findViewById(R.id.loginButton);
        myLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etMyUserName = findViewById(R.id.editTextUsername);
                etUsername = etMyUserName.getText().toString();
                EditText etMyPassword = findViewById(R.id.editTextPassword);
                etPassword = etMyPassword.getText().toString();

                Log.v(TAG, "Login:" + etUsername + " " + etPassword);

                /* Save user data*/
                User user = new User();
                user.setUsername(etUsername);
                user.setPassword(etPassword);
                Bundle myBundle = new Bundle();
                myBundle.putString("Username", user.getUsername());
                myBundle.putString("Password", user.getPassword());


            }
        });

        /* Create User Button */
        Button myNewUserButton = findViewById(R.id.newUserButton);
        myNewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Login.this, SignUp.class);
                startActivity(myIntent);
            }
        });
    }
}
