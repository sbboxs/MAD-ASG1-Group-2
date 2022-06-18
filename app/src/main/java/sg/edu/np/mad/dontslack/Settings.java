package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //back
        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(v -> {
            Intent myIntent = new Intent(Settings.this, HomePage.class);
            startActivity(myIntent);
        });

        ImageView notificationPage = findViewById(R.id.notificationView);
        notificationPage.setOnClickListener(view -> Toast.makeText(Settings.this,"Not available yet.",Toast.LENGTH_SHORT).show());

        ImageView aboutPage = findViewById(R.id.aboutView);
        aboutPage.setOnClickListener(view -> {
            Intent myIntent = new Intent(Settings.this, About.class);
            startActivity(myIntent);
        });
    }
}