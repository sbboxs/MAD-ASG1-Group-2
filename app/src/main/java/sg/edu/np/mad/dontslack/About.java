package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

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
        goBackButton.setOnClickListener(v -> {
            Intent myIntent2 = new Intent(About.this, Settings.class);
            startActivity(myIntent2);
        });
        TextView tv = (TextView) findViewById(R.id.termNCondition);
        tv.setText(Html.fromHtml("<a href=https://westwq.github.io/MADPrivacy/> Term & Condition "));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }
}