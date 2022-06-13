package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button toDOListButton = findViewById(R.id.toDoListButton);
        toDOListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(HomePage.this,ToDoList.class);
                startActivity(myIntent);
            }
        });



    }

}