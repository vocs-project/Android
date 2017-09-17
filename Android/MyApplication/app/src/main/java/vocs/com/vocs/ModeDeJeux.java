package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;
import android.widget.Toast;

import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.tradliste;

public class ModeDeJeux extends AppCompatActivity {

    Intent ModeDeJeux = getIntent();
    Button TraductionNormale,retourModePrinc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_de_jeux);

        TraductionNormale=(Button) findViewById(R.id.TraductionNormale);
        retourModePrinc=(Button) findViewById(R.id.retourModePrinc);

        TraductionNormale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent TraductionNormale = new Intent (ModeDeJeux.this, ChoixListe.class);
                startActivity(TraductionNormale);
            }
        });
        retourModePrinc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retourModePrinc = new Intent (ModeDeJeux.this, PagePrinc.class);
                startActivity(retourModePrinc);
            }
        });
    }

}

