package sg.edu.np.mad.dontslack;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
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
        createNotificationChannel();
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
            //If changes is made but not save
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

            expandableNotification.setVisibility(View.VISIBLE);
        }
        else{
            expandableNotification.setVisibility((View.GONE));
        }

        //If allow notification, display notify timing selector
        notificationAllowSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ifChanges = true;
                if(isChecked){
                    expandableNotification.setVisibility(View.VISIBLE);
                }
                else{
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

        //Save notification time if have
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ifChanges){
                    @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Key_NotifyTime, notificationTiming.getText().toString());
                    editor.putBoolean(KEY_IfNotification, notificationAllowSwitch.isChecked());
                    editor.apply();
                    setAlarmNotify(notificationAllowSwitch.isChecked());
                    ifChanges = false;
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
        int importance= NotificationManager.IMPORTANCE_DEFAULT; //Notification will still show even in lock screen
        NotificationChannel channel = new NotificationChannel("notifyDon'tSlack",name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Toast.makeText(this, "Notification channel created", Toast.LENGTH_SHORT).show();

    }

    public void showDateTimeDialog(EditText date_time) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view1, hourOfDay, minute) -> {
            calendar.set(java.util.Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(java.util.Calendar.MINUTE, minute);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            date_time.setText(simpleDateFormat.format(calendar.getTime()));
        };
        new TimePickerDialog(this, timeSetListener, calendar.get(java.util.Calendar.HOUR_OF_DAY), calendar.get(java.util.Calendar.MINUTE), false).show();
        ifChanges = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarmNotify(boolean ifNotify){
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //Setting up notification
        Intent myIntent = new Intent(this, ReminderBroadcast.class);
        //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent myPIntent = PendingIntent.getBroadcast(this,0,myIntent,0);

        //Check if notification allow
        if(ifNotify){
            //If allow notification, set up all data needed
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            String time = sharedPreferences.getString(Key_NotifyTime,"08:00");
            int hour = Integer.parseInt(time.substring(0,2));
            int minute = Integer.parseInt(time.substring(3,5));
            Log.v(TAG, time);
            Log.v(TAG, String.valueOf(minute));

            //Set the notify time
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);

            //Compare calendar to actual time, if time pass then will show next day
            if(calendar.before((Calendar.getInstance()))){
                calendar.add(Calendar.DAY_OF_MONTH,1);
                Log.v(TAG, "NOOO");

            }

            Log.v(TAG, String.valueOf(calendar.getTime()));
            Log.v(TAG, String.valueOf(calendar.getTimeInMillis()));
            //set Inexact as this is a daily notification.
            //Inexact notification might have a few minutes delay
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),myPIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP,1000,myPIntent);
            //Due to android OS, as of API 19 now all repeating alarm is not exact
            //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,myPIntent);
            Toast.makeText(this, "Alarm set Successfully", Toast.LENGTH_SHORT).show();
        }

        //Cancel notification if not allow
        else{
            //If no alarm is set
            if(alarmManager == null){
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            }
            else{
                alarmManager.cancel(myPIntent);
                Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}