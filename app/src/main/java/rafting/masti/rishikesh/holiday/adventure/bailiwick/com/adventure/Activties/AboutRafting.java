package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class AboutRafting extends AppCompatActivity {

    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rafting);

        getUiObject();

        playVideo();

    }


    private void getUiObject(){
        videoView = findViewById(R.id.videoView);
        setVideoViewHeight();
    }

    private void setVideoViewHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;
        videoView.setLayoutParams(new FrameLayout.LayoutParams(w,h));

    }

    private void playVideo(){
        try {
            /*View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);*/

            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
            videoView.setVideoURI(video);

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                }
            });

            videoView.start();


        } catch (Exception e) {
            Log.e("Exc ==> ",e.getMessage());
           // next();
        }
    }



}
