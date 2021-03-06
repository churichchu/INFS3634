package com.example.richard.assessment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
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
    ImageView pass1, pass2, pass3, pass4;
    AlertDialog.Builder feedback;
    Intent intent, passingMod;
    public static final String passedMod = "123124124123";

    static int modNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);
        back = (Button) findViewById(R.id.back);
        name = (TextView) findViewById(R.id.app_name);
        modules = (TextView) findViewById(R.id.modules);


        mod1 = (Button) findViewById(R.id.mod1);
        mod2 = (Button) findViewById(R.id.mod2);
        mod3 = (Button) findViewById(R.id.mod3);
        mod4 = (Button) findViewById(R.id.mod4);

        pass1 = (ImageView) findViewById(R.id.pass1);
        pass2 = (ImageView) findViewById(R.id.pass2);
        pass3 = (ImageView) findViewById(R.id.pass3);
        pass4 = (ImageView) findViewById(R.id.pass4);

        pass1.setVisibility(View.INVISIBLE);
        pass2.setVisibility(View.INVISIBLE);
        pass3.setVisibility(View.INVISIBLE);
        pass4.setVisibility(View.INVISIBLE);

        intent = getIntent();


        if(intent != null) {
            setButtonText();
            //mark correct module as complete
            if(intent.hasExtra("pass")) {
                getFeedback();
                if(modNumber == 1) {
                    pass1.setVisibility(View.VISIBLE);
                } else if(modNumber == 2) {
                    pass2.setVisibility(View.VISIBLE);
                } else if(modNumber == 3) {
                    pass3.setVisibility(View.VISIBLE);
                } else if(modNumber == 4) {
                    pass4.setVisibility(View.VISIBLE);
                }

            }
            else if (intent.hasExtra("fail")) {
                getFeedback();
            }
        }
    }

    public void setButtonText() {
        Resources res = getResources();
        buttonText = Arrays.asList(res.getStringArray(R.array.all_modules));
        mod1.setText(buttonText.get(0));
        mod2.setText(buttonText.get(1));
        mod3.setText(buttonText.get(2));
        mod4.setText(buttonText.get(3));
    }

    //get information about video before loading video activity. Set selected module to modNumber
    //attribute.
    public void onButtonClick(View view) {
        String module;
        switch (view.getId()) {
            case R.id.mod1:
                module = String.valueOf(mod1.getText());
                getVideoId(module);
                modNumber = 1;

                break;

            case R.id.mod2:
                module = String.valueOf(mod2.getText());
                getVideoId(module);
                modNumber = 2;
                break;

            case R.id.mod3:
                module = String.valueOf(mod3.getText());
                getVideoId(module);
                modNumber = 3;
                break;

            case R.id.mod4:
                module = String.valueOf(mod4.getText());
                getVideoId(module);
                modNumber = 4;
                break;
        }

    }

    //retrieve information about video associated to module and add to extras in intents
    public void getVideoId(String module) {
        vidModel = new VideoModel();
        vidModel.getVideoModel();
        for (int i = 0; i < buttonText.size(); i++) {
            if (module.equals(vidModel.getVideoModule().get(i))) {
                Intent intent = new Intent(ModuleActivity.this, ModuleVideo.class);
                intent.putExtra("video_id", vidModel.getVideoId().get(i));
                intent.putExtra("video_title", vidModel.getVideoTitle().get(i));
                intent.putExtra("mod_desc", vidModel.getModuleDescription().get(i));
                intent.putExtra("module_name", vidModel.getVideoModule().get(i));
                startActivity(intent);
            }
        }
    }

    //naviagte back to application start screen
    public void onBack(View v) {
        Intent i = new Intent(ModuleActivity.this, MainActivity.class);
        this.startActivity(i);

    }

    //return a dialog box object to inform the user of their quiz results
    public AlertDialog.Builder getFeedback() {
        feedback = new AlertDialog.Builder(this);
        feedback.setTitle(getString(R.string.mcq_results));
        if(intent.hasExtra("pass")) {
            feedback.setMessage(getString(R.string.Pass_MCQ));
        } else if (intent.hasExtra("fail")) {
            feedback.setMessage(getString(R.string.module_fail));
        }
        feedback.setPositiveButton("OK", null);
        feedback.setCancelable(true);
        feedback.create().show();
        return feedback;
    }


}
