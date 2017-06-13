package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;

import static vocs.com.vocs.R.id.score;


public class score extends AppCompatActivity {

    TextView scorebon,scorett;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vocs);
        scorebon= (TextView) findViewById(R.id.scorebon);
        scorett= (TextView) findViewById(R.id.scorett);
        ok= (Button) findViewById(R.id.ok);

        Bundle b = getIntent().getExtras();
        int value = -1;

        if(b != null)
            value = b.getInt("key");
            scorebon.setText(String.valueOf(value));

        Bundle t = getIntent().getExtras();
        int valut = -1;

        if(t != null){
            valut = t.getInt("autre");
            scorett.setText(String.valueOf(valut));
        }


        retour_pp();
    }

    public void retour_pp (){
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retour = new Intent (score.this, PagePrinc.class);
                startActivity(retour);
    }});
}}
