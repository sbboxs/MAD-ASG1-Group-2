package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_LOGIN = "accountStatus";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Hiding the top bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        boolean IsLogin = sharedPreferences.getBoolean(KEY_LOGIN,false);

        //Profile and login button
        TextView profileTextView = findViewById(R.id.ifLoginMessage);
        //If login it will be profile button
        if(IsLogin){
            profileTextView.setText("Profile");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(v -> {
                Intent myIntent = new Intent(HomePage.this, Profile.class);
                startActivity(myIntent);
            });
        }//Else it will be login button
        else{
            profileTextView.setText("Login");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(v -> {
                Intent myIntent = new Intent(HomePage.this, Login.class);
                startActivity(myIntent);
            });
        }

        //To do list button
        Button ToDOListButton = findViewById(R.id.toDoListButton);
        ToDOListButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this,ToDoList.class);
            startActivity(myIntent);
        });

        //Notes button
        Button NotesButton = findViewById(R.id.notesButton);
        NotesButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this, Notes.class);
            startActivity(myIntent);
        });

        //Calendar button
        Button CalendarButton = findViewById(R.id.calendarButton);
        CalendarButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this, Calendar.class);
            startActivity(myIntent);
        });

        //Contact Me button
        ImageView contactImage = findViewById(R.id.contactButton);
        contactImage.setOnClickListener(view -> {
            Intent myIntent = new Intent(HomePage.this, Contact.class);
            startActivity(myIntent);
        });

        //Setting button
        ImageView settingImage = findViewById(R.id.settingsButton);
        settingImage.setOnClickListener(view -> {
            Intent myIntent = new Intent(HomePage.this, Settings.class);
            startActivity(myIntent);
        });
    }

}