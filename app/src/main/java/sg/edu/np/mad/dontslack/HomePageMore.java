package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class HomePageMore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_more);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //music player button
        Button musicplayerBtn = findViewById(R.id.musicbtn);
        musicplayerBtn.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePageMore.this,MusicPlayer.class);
            startActivity(myIntent);
        });

    }
}