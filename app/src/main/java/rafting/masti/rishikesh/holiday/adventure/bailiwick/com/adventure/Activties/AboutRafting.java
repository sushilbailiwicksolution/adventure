package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.VideoView;

import java.util.Objects;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class AboutRafting extends AppCompatActivity implements View.OnClickListener {

    VideoView videoView;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rafting);
        from  = getIntentData();
        getUiObject();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                playVideo(from);
            }
        });
    }

    private String getIntentData(){
        Log.e("From",Objects.requireNonNull(getIntent().getExtras()).getString("from"));
        return Objects.requireNonNull(getIntent().getExtras()).getString("from");
    }

    private void getUiObject(){
        videoView = findViewById(R.id.videoView);
       // setVideoViewHeight();
       videoView.setOnClickListener(this);
    }

    private void setVideoViewHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;
        videoView.setLayoutParams(new LinearLayout.LayoutParams(w,h-400));
    }

    private void playVideo(String fromWhere){
        try {
            /*View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);*/
            Uri video;

            if (fromWhere.equalsIgnoreCase("rafting")){
                video = Uri.parse("http://www.indianadventurepackages.com/assets/videos/rafting-video.mp4");
                videoView.setVideoURI(video);
                videoView.start();
            }else if (fromWhere.equalsIgnoreCase("Bungee")){
                video = Uri.parse("http://www.indianadventurepackages.com/assets/videos/rafting-video.mp4");
                videoView.setVideoURI(video);
                videoView.start();
            }

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                }
            });

        } catch (Exception e) {
            Log.e("Exc ==> ",e.getMessage());
           // next();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.videoView:
                if (videoView.isPlaying()){
                    videoView.stopPlayback();
                }else if (!videoView.isPlaying()){
                    videoView.start();
                }
                break;
        }
    }
}
