package com.example.richard.assessment;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class ModuleActivity extends AppCompatActivity {

    Button mod1, mod2, mod3, mod4, back;
    TextView name;
    TextView modules;
    List<String> buttonText;
    VideoModel vidModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        back = (Button) findViewById(R.id.back);
        name = (TextView) findViewById(R.id.app_name);
        modules = (TextView) findViewById(R.id.modules);



        prepareButtonText();
        mod1 = (Button) findViewById(R.id.mod1);
        mod1.setText(buttonText.get(0));
        mod2 = (Button) findViewById(R.id.mod2);
        mod2.setText(buttonText.get(1));
        mod3 = (Button) findViewById(R.id.mod3);
        mod3.setText(buttonText.get(2));
        mod4 = (Button) findViewById(R.id.mod4);
        mod4.setText(buttonText.get(3));
    }

    public void prepareButtonText() {
        Resources res = getResources();
        buttonText = Arrays.asList(res.getStringArray(R.array.all_modules));
    }

    public void onButtonClick(View view) {
        String module = null;
        switch (view.getId()) {
            case R.id.mod1:
                module = String.valueOf(mod1.getText());
                getVideoId(module);
                break;

            case R.id.mod2:
                module = String.valueOf(mod2.getText());
                getVideoId(module);
                break;

            case R.id.mod3:
                module = String.valueOf(mod3.getText());
                getVideoId(module);

                break;

            case R.id.mod4:
                module = String.valueOf(mod4.getText());
                getVideoId(module);
                break;
        }
    }

    public void getVideoId(String module) {
        vidModel = new VideoModel();
        vidModel.getVideoModel();
        for (int i = 0; i < buttonText.size(); i++) {
            if (module.equals(vidModel.getVideoModule().get(i))) {
                Intent intent = new Intent(ModuleActivity.this, ModuleVideo.class);
                intent.putExtra("video_id", vidModel.getVideoId().get(i));
                intent.putExtra("video_title", vidModel.getVideoTitle().get(i));
                intent.putExtra("mod_desc", vidModel.getModuleDescription().get(i));
                startActivity(intent);
            }
        }
    }

    public void onBack(View v) {
        Intent i = new Intent(ModuleActivity.this, MainActivity.class);
        this.startActivity(i);

    }
}
