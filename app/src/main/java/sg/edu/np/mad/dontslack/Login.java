package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private final String TAG = "Login Activity";
    private static String etUsername;
    private static String etPassword;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_LOGIN = "accountStatus";
    private static final String KEY_USERNAME = "userName";

    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(v -> {
            Intent myIntent = new Intent(Login.this, HomePage.class);
            startActivity(myIntent);
        });

        /* Login Button */
        Button myLoginButton = findViewById(R.id.loginButton);
        myLoginButton.setOnClickListener(view -> {
            EditText etMyUserName = findViewById(R.id.editTextUsername);
            etUsername = etMyUserName.getText().toString();
            EditText etMyPassword = findViewById(R.id.editTextPassword);
            etPassword = etMyPassword.getText().toString();
            //Check if all fields are filled
            if(!etUsername.equals("") && !etPassword.equals("")){
                //Check is valid login credentials
                if(isValidCredentials(etUsername,etPassword)){
                    //Save user data and pass it to intent
                    User user = new User();
                    user.setUsername(etUsername);
                    user.setPassword(etPassword);
                    sharedPreferences =  getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_LOGIN,true);
                    editor.putString(KEY_USERNAME,user.getUsername());
                    editor.apply();
                    Intent myIntent = new Intent(Login.this,HomePage.class);
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
        });

        /* Create User Button */
        Button myNewUserButton = findViewById(R.id.newUserButton);
        myNewUserButton.setOnClickListener(view -> {
            Intent myIntent = new Intent(Login.this, SignUp.class);
            startActivity(myIntent);
            //test
        });
    }
    //To check if the user account is exist
    public boolean isValidCredentials(String userName, String password){
        User dbData = dbHandler.findUser(userName);
        //If Username is found
        if (dbData != null){
            //Check if user password is correct
            if(dbData.getUsername().equals(userName) && dbData.getPassword().equals(password)){
                Toast.makeText(Login.this,"Login Success", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        //If invalid login credentials
        return false;
    }
}
