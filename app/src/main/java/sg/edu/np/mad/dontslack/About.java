package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ImageView goBackButton = findViewById(R.id.backHome3);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(About.this, Settings.class);
                startActivity(myIntent2);
            }
        });
    }
}