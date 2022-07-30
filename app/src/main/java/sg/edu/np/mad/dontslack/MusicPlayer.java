package sg.edu.np.mad.dontslack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener{
    //MediaPlayer player;

    private Intent serviceIntent;
    private Button buttonStart, buttonStop, buttonPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonPause = findViewById(R.id.buttonPause);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonPause.setOnClickListener(this);

        serviceIntent = new Intent(getApplicationContext(), MyService.class);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.buttonStart:
                startService(new Intent(getApplicationContext(), MyService.class));
                break;
            case R.id.buttonStop:
                stopService(new Intent(getApplicationContext(), MyService.class));
                break;
        }
    }

    /*
    public void play(View v){
        if (player == null){
            player = MediaPlayer.create(this, R.raw.song1);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    public void pause(View v){
        if(player !=null){
            player.pause();
        }
    }
    public void stop(View v){
        stopPlayer();
    }
    private void stopPlayer(){
        if (player != null){
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        stopPlayer();
    }

    @Override
    public void onClick(View view) {

    }*/
}