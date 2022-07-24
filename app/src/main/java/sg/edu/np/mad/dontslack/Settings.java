package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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
        RelativeLayout notificationPage = findViewById(R.id.aboutRelativeLayout);
        notificationPage.setOnClickListener(view -> {
            Intent myIntent = new Intent(this,Notification.class);
            startActivity(myIntent);
        });

        //About button
        RelativeLayout aboutPage = findViewById(R.id.notificationRelativeLayout);
        aboutPage.setOnClickListener(view -> {
            Intent myIntent = new Intent(Settings.this, About.class);
            startActivity(myIntent);
        });
    }
}