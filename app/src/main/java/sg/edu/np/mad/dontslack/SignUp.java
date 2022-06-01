package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        /* Create User */



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