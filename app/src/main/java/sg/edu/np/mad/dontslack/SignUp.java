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

public class SignUp extends AppCompatActivity {
    private final String TAG = "Sign uP Activity";

    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /* Create User */
        //editTextCreateUsername
        Button myCreateButton = findViewById(R.id.createButton);
        myCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText createUsername = findViewById(R.id.editTextCreateUsername);
                EditText createPassword = findViewById(R.id.editTextCreatePassword);
                EditText confirmPassword = findViewById(R.id.editTextConfirmPassword);
                String Username = createUsername.getText().toString();
                String Password = createPassword.getText().toString();
                String ConfirmPassword = confirmPassword.getText().toString();
                //Checking if all fields is filled
                if (!Username.equals("") && !Password.equals("") && !ConfirmPassword.equals("")){
                    //Checking if password equals to confirm password
                    if (Password.equals(ConfirmPassword)){
                        User dbData = dbHandler.findUser(Username);
                        Log.v(TAG,String.valueOf(dbData));
                        //Checking if the username exists
                        if (dbData == null){
                            User newUserAccount = new User();
                            newUserAccount.setUsername(Username);
                            newUserAccount.setPassword(Password);
                            dbHandler.addUser(newUserAccount);
                            Toast.makeText(SignUp.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(SignUp.this, Login.class);
                            startActivity(myIntent);
                        }
                        else {
                            Toast.makeText(SignUp.this, "User is already exist! Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUp.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignUp.this, "Please ensure all fields is filled!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /* Back to Login */
        Button myBackButton = findViewById(R.id.backButton);
        myBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(SignUp.this, Login.class);
                startActivity(myIntent);
            }
        });
    }
}