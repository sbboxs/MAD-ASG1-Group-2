package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_LOGIN = "accountStatus";
    private static final String KEY_USERNAME = "userName";
    DBHandler dbHandler = new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        String Username = sharedPreferences.getString(KEY_USERNAME,"");
        User user = dbHandler.findUser(Username);

        TextView profileUsername = findViewById(R.id.textProfileUsername);
        profileUsername.setText(user.getUsername());

        TextView profilePassword = findViewById(R.id.textProfilePassword);
        profilePassword.setText(user.getPassword());

        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Profile.this, HomePage.class);
                startActivity(myIntent);
            }
        });
        TextView deleteAccountButton = findViewById(R.id.deleteAccountButton);
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        TextView logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(Profile.this,"Log out successfully",Toast.LENGTH_SHORT).show();
                finish();
                Intent myIntent = new Intent(Profile.this,HomePage.class);
                startActivity(myIntent);
            }
        });
        /*
        EditText etMyUserName = findViewById(R.id.editTextUsername);
        EditText profileName = findViewById(R.id.editTextProfileUsername);
        profileName.setText(etMyUserName.getText());
        */
    }
}