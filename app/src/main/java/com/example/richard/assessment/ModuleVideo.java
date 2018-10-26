package com.example.richard.assessment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ModuleVideo extends YouTubeBaseActivity {

    private YouTubePlayerView youTubeView;
    private TextView moduleName, moduleDesc;
    private Button proc_mcq, back;
    private final int RECOVERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_video);

        //initialise UI elements
        youTubeView = findViewById(R.id.youtube_player);
        moduleName = (TextView) findViewById(R.id.mod_title);
        moduleName.setText(getIntent().getStringExtra("video_title"));
        moduleDesc = (TextView) findViewById(R.id.mod_desc);
        moduleDesc.setText(getIntent().getStringExtra("mod_desc"));
        proc_mcq = (Button) findViewById(R.id.proc_mcq);
        proc_mcq.setActivated(false);
        back = (Button) findViewById(R.id.back);


        //this button allows the user to proceed to the mutliple choice quiz on click
        proc_mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!proc_mcq.isActivated()) {
                    Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    proc_mcq.startAnimation(shake);
                    Toast.makeText(getApplicationContext(), R.string.vid_incomplete, Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(ModuleVideo.this, QuizMainActivity.class);
                    i.putExtra("module_name", getIntent().getStringExtra("module_name"));
                    startActivity(i);
                }
            }
        });

        //go back to the module activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModuleVideo.this, ModuleActivity.class);
                startActivity(i);
            }
        });

        //initialize the YouTubePlayerView and set behaviour for successful and failed initializations.
        youTubeView.initialize(getString(R.string.youtube_api), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                player.setPlaybackEventListener(playBackEventListener);
                player.setPlayerStateChangeListener(playerStateChangeListener);

                if (!wasRestored) {
                    player.cueVideo(getIntent().getStringExtra("video_id"));
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
                if (errorReason.isUserRecoverableError()) {
                    errorReason.getErrorDialog(getParent(), RECOVERY_REQUEST).show();
                } else {
                    String error = getString(R.string.vid_error);
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //respond to different playback events on the YouTube video

    private YouTubePlayer.PlaybackEventListener playBackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {

        }

        @Override
        public void onPlaying() {

        }

        @Override
        public void onSeekTo(int arg0) {

        }

        public void onStopped() {
        }

        @Override
        public void onPaused() {

        }
    };


    //responds to changes in the state of the YouTube Player.
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        //the user may only progress to the multiple choice quiz once the video has ended
        @Override
        public void onVideoEnded() {
            proc_mcq.setActivated(true);
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
