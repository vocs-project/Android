package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static vocs.com.vocs.R.id.progressBar;

public class vocs_logo extends AppCompatActivity {

    Intent vocs_logo = getIntent();
    ImageButton bout;
    ProgressBar progressbar;
    CountDownTimer mCountDownTimer;
    int m=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocs_logo);

        bout = (ImageButton) findViewById(R.id.boutonvocs);
        progressbar= (ProgressBar) findViewById(progressBar);



        progressbar.getProgressDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.SRC_IN);
        progressbar.setProgress(m);
        mCountDownTimer=new CountDownTimer(3000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ m + millisUntilFinished);
                m++;
                progressbar.setProgress((int)m*100/(5000/1000));

            }

            @Override
            public void onFinish() {
                m++;
                progressbar.setProgress(100);
                if(progressbar.getProgress()== progressbar.getMax()){
                    Intent intent  = new Intent(vocs_logo.this , PagePrinc.class);
                    startActivity(intent);
                }
            }
        };
        mCountDownTimer.start();
    }
}
