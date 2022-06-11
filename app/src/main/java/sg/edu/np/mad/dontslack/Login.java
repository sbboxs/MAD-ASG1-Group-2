package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private final String TAG = "Login Activity";
    private static String etUsername;
    private static String etPassword;
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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
                if(!etUsername.equals("") && !etPassword.equals("")){
                    //Check is valid login credentials
                    if(isValidCredentials(etUsername,etPassword)){
                        //Save user data and pass it to intent
                        User user = new User();
                        user.setUsername(etUsername);
                        user.setPassword(etPassword);
                        Bundle myBundle = new Bundle();
                        Intent myIntent = new Intent(Login.this,HomePage.class);
                        myIntent.putExtra("Bundle",myBundle);
                        startActivity(myIntent);
                    }
                    //If invalid login credentials
                    else{
                        Toast.makeText(Login.this, "Invalid Login Credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Please ensure all fields is filled!", Toast.LENGTH_SHORT).show();
                }

                Log.v(TAG, "Login:" + etUsername + " " + etPassword);
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
    public boolean isValidCredentials(String userName, String password){
        User dbData = dbHandler.findUser(userName);
        //If Username is found
        if (dbData != null){
            //Check if user password is correct
            if(dbData.getUsername().equals(userName) && dbData.getUsername().equals(password)){
                Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        //If invalid login credentials
        return false;
    }
}
