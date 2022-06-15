package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Set;
import java.util.prefs.PreferenceChangeEvent;

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

        TextView profileTextView = findViewById(R.id.ifLoginMessage);
        if(IsLogin){
            profileTextView.setText("Profile");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(HomePage.this, Profile.class);
                    startActivity(myIntent);
                }
            });
        }
        else{
            profileTextView.setText("Login");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(HomePage.this, Login.class);
                    startActivity(myIntent);
                }
            });
        }

        Button ToDOListButton = findViewById(R.id.toDoListButton);
        ToDOListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this,ToDoList.class);
                startActivity(myIntent);
            }
        });

        Button NotesButton = findViewById(R.id.notesButton);
        NotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, Notes.class);
                startActivity(myIntent);
            }
        });

        Button CalendarButton = findViewById(R.id.calendarButton);
        CalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this, Calendar.class);
                startActivity(myIntent);
            }
        });




        ImageView contactImage = findViewById(R.id.contactButton);
        contactImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomePage.this, Contact.class);
                startActivity(myIntent);
            }
        });


        /* ImageView faqImage = findViewById(R.id.faqButton);
        faqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomePage.this, FAQ.class);
                startActivity(myIntent);
            }
        }); */

        ImageView settingImage = findViewById(R.id.settingsButton);
        settingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomePage.this, Settings.class);
                startActivity(myIntent);
            }
        });
    }

}