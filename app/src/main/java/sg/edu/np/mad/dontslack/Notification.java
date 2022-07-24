package sg.edu.np.mad.dontslack;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Notification extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "myPref";
    private final String TAG = "Notification Setting";
    private static final String KEY_IfNotification = "Notification";
    private static final String Key_NotifyTime = "NotificationTiming";
    public boolean ifChanges = false;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);


        /* Hiding the top bar */
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        //Defining item in Views
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch notificationAllowSwitch= (Switch) findViewById(R.id.notificationMessage);
        ConstraintLayout expandableNotification = findViewById(R.id.notificationExpandable);
        EditText notificationTiming = findViewById(R.id.notificationTimePicker);
        Button saveButton = findViewById(R.id.saveNotificationButton);

        //Back button
        ImageView goBackButton = findViewById(R.id.backHome4);
        goBackButton.setOnClickListener(v -> {
            if(ifChanges){
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Changes is found");
                alertDialog.setMessage("Are you sure you want to back without saving your changes?");

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialog, which) -> {
                    alertDialog.dismiss();
                });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes, I'm sure.", (dialog, which) -> {
                    Intent myIntent = new Intent(Notification.this, Settings.class);
                    startActivity(myIntent);
                });
                alertDialog.show();
            }
            else{
                Intent myIntent = new Intent(Notification.this, Settings.class);
                startActivity(myIntent);
            }

        });

        //Setting switch and alarm
        if(sharedPreferences.getBoolean(KEY_IfNotification,false)){
            notificationAllowSwitch.setChecked(true);
            createNotificationChannel();
        }

        if(sharedPreferences.getBoolean(KEY_IfNotification,false)){
            expandableNotification.setVisibility(View.VISIBLE);
        }
        else{
            expandableNotification.setVisibility((View.GONE));
        }

        //If allow notification, display notify timing selector
        notificationAllowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IfNotification,true);
                    expandableNotification.setVisibility(View.VISIBLE);
                    editor.apply();
                }
                else{
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IfNotification,false);
                    editor.apply();
                    expandableNotification.setVisibility(View.GONE);
                }
            }
        });

        //Setting notify time picker
        notificationTiming.setText(sharedPreferences.getString(Key_NotifyTime,"08:00"));
        notificationTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(notificationTiming);
            }
        });
        //Save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifChanges){
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Key_NotifyTime, notificationTiming.getText().toString());
                    Toast.makeText(Notification.this,"Changes is saved.",Toast.LENGTH_SHORT).show();
                    ifChanges = false;
                    editor.apply();
                    setAlarmNotify();
                }
                else{
                    Toast.makeText(Notification.this,"No changes is found.",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(){

        CharSequence name = "Don'tSlackReminderChannel";
        String description = "Channel for Don't Slack Reminder";
        int importance= NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notifyDon'tSlack",name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void showDateTimeDialog(EditText date_time) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            calendar.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(java.util.Calendar.MINUTE, minute);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            date_time.setText(simpleDateFormat.format(calendar.getTime()));
        };
        new TimePickerDialog(this, timeSetListener, calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE), true).show();
        ifChanges = true;
    }

    public void setAlarmNotify(){
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        String time = sharedPreferences.getString(Key_NotifyTime,"08:00");
        int hour = Integer.parseInt(time.substring(0,2));
        int minute = Integer.parseInt(time.substring(3));
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);



        if(Calendar.getInstance().after(calendar)){
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        Intent alarmIntent = new Intent(this, ReminderBroadcast.class);
        alarmIntent.setAction("MY_NOTIFICATION_MESSAGE");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }


    }
}