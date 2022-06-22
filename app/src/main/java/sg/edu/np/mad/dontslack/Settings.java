package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Back button
        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(v -> {
            Intent myIntent = new Intent(Settings.this, HomePage.class);
            startActivity(myIntent);
        });

        //Notification button
        RelativeLayout notificationPage = findViewById(R.id.notificationRelativeLayout);
        notificationPage.setOnClickListener(view -> Toast.makeText(this,"Feature Not Available Yet", Toast.LENGTH_SHORT).show());

        //About button
        RelativeLayout aboutPage = findViewById(R.id.aboutRelativeLayout);
        aboutPage.setOnClickListener(view -> {
            Intent myIntent = new Intent(Settings.this, About.class);
            startActivity(myIntent);
        });
    }
}