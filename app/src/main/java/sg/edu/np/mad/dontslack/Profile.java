package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ImageView backHomePage = findViewById(R.id.backHome);
        backHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Profile.this, HomePage.class);
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