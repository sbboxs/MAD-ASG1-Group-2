package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomePage extends AppCompatActivity {
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "myPref";
    private static final String KEY_LOGIN = "accountStatus";
    private String quote;
    private String author;
    Dialog dialog;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);


        SharedPreferences prefs  = getSharedPreferences("prefs",MODE_PRIVATE);
        dialog = new Dialog(this);

        // Hiding the top bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        boolean IsLogin = sharedPreferences.getBoolean(KEY_LOGIN,false);

        //Profile and login button
        TextView profileTextView = findViewById(R.id.ifLoginMessage);
        //If login it will be profile button
        if(IsLogin){
            profileTextView.setText("Profile");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(v -> {
                Intent myIntent = new Intent(HomePage.this, Profile.class);
                startActivity(myIntent);
            });
        }//Else it will be login button
        else{
            profileTextView.setText("Login");
            ImageView profileImage = findViewById(R.id.profileButton);
            profileImage.setOnClickListener(v -> {
                Intent myIntent = new Intent(HomePage.this, Login.class);
                startActivity(myIntent);
            });
        }

        //To do list button
        Button ToDOListButton = findViewById(R.id.toDoListButton);
        ToDOListButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this,ToDoList.class);
            startActivity(myIntent);
        });

        //Notes button
        Button NotesButton = findViewById(R.id.notesButton);
        NotesButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this, Notes.class);
            startActivity(myIntent);
        });

        //Calendar button
        Button CalendarButton = findViewById(R.id.calendarButton);
        CalendarButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this, Calendar.class);
            startActivity(myIntent);
        });

        //Timer button
        Button TimerButton = findViewById(R.id.timerButton);
        TimerButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(HomePage.this, Timer.class);
            startActivity(myIntent);
        });

        //Contact Me button
        ImageView contactImage = findViewById(R.id.contactButton);
        contactImage.setOnClickListener(view -> {
            Intent myIntent = new Intent(HomePage.this, Contact.class);
            startActivity(myIntent);
        });

        //Setting button
        ImageView settingImage = findViewById(R.id.settingsButton);
        settingImage.setOnClickListener(view -> {
            Intent myIntent = new Intent(HomePage.this, Settings.class);
            startActivity(myIntent);
        });

        //switch to home page more
        Switch mySwitch = findViewById(R.id.switch1);
        mySwitch.setOnClickListener(view -> {
            Intent myIntent = new Intent(HomePage.this, HomePageMore.class);
            startActivity(myIntent);
        });


        //Get Data From API
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://zenquotes.io/api/today";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    quote = "";
                    author = "";
                    try {
                        JSONObject data = response.getJSONObject(0);
                        quote = data.getString("q");
                        author = data.getString("a");
                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);
                    String currentDate = sdf.format(new Date());
                    if(prefs.getString("LAST_LAUNCH_DATE", "noDate").contains(currentDate))
                    {
                        //Does Nothing if the date is the same
                    }
                    else
                    {
                        showStartDialog(quote,author);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("LAST_LAUNCH_DATE", currentDate);
                        editor.apply();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(HomePage.this, "Error has occurred", Toast.LENGTH_SHORT).show();
                }
            });
                      queue.add(request);

    }

    //Method for show dialog when start up
    private String showStartDialog(String q , String a) {

        dialog.setContentView(R.layout.quote_dialog_layout);
        TextView quoteTxt = dialog.findViewById(R.id.quoteText);
        quoteTxt.setText(q + " ~ " + a);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        return q;
    }

    //Previous Dialog Box
//        new AlertDialog.Builder(this)
//                .setTitle("Inspirational Quote of the day")
//                .setMessage(q)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                })
//                .create().show();
//
//        return q;
    }