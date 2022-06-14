package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }
}