package com.example.richard.assessment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        youTubeView = findViewById(R.id.youtube_player);
        moduleName = (TextView) findViewById(R.id.mod_title);
        moduleName.setText(getIntent().getStringExtra("video_title"));
        moduleDesc = (TextView) findViewById(R.id.mod_desc);
        moduleDesc.setText(getIntent().getStringExtra("mod_desc"));
        proc_mcq = (Button) findViewById(R.id.proc_mcq);
        proc_mcq.setVisibility(View.INVISIBLE);
        back = (Button) findViewById(R.id.back);

        proc_mcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModuleVideo.this, QuizMainActivity.class);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModuleVideo.this, ModuleActivity.class);
                startActivity(i);
            }
        });



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

        @Override
        public void onVideoEnded() {
            proc_mcq.setVisibility(View.VISIBLE);
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
