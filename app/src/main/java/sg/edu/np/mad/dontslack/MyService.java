package sg.edu.np.mad.dontslack;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyService extends Service {
    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        mMediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.song1);
        mMediaPlayer.setLooping(false);
    }

    public void onStart(Intent intent, int startid){
        Toast.makeText(this, "Music started", Toast.LENGTH_SHORT).show();
        mMediaPlayer.start();
    }

    public void onPause(){
        mMediaPlayer.pause();
    }

    public void onDestroy(){
        Toast.makeText(this, "Music stopped", Toast.LENGTH_SHORT).show();
        mMediaPlayer.stop();
    }

}
