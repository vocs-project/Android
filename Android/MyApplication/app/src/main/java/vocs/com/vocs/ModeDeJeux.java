package vocs.com.vocs;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import java.text.Normalizer;
import android.content.Intent;
import android.widget.Toast;


import static android.R.attr.button;
import static vocs.com.vocs.R.id.TraductionNormale;
import static vocs.com.vocs.R.id.parametres;


public class ModeDeJeux extends AppCompatActivity {

    Intent ModeDeJeux = getIntent();
    Button TraductionNormale,qcm;
    BottomNavigationView BottomBar;
    ImageButton parametres,retourModePrinc;
    String idreçu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_de_jeux);

        TraductionNormale=(Button) findViewById(R.id.TraductionNormale);
        retourModePrinc=(ImageButton) findViewById(R.id.retourarriere);
        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);
        qcm=(Button) findViewById(R.id.qcm);

        Bundle d = getIntent().getExtras();

        if(d != null) {
            idreçu = d.getString("id");
        }

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (ModeDeJeux.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (ModeDeJeux.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (ModeDeJeux.this, ViewListContents.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                        break;
                }
                return true;
            }
        });

        TraductionNormale.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent TraductionNormale = new Intent (ModeDeJeux.this, ChoixListeAvantJeux.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putInt("key",1);
               TraductionNormale.putExtras(b);
                startActivity(TraductionNormale);
                finish();
            }
        });
        qcm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent qcm = new Intent (ModeDeJeux.this, ChoixListeAvantJeux.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putInt("key",2);
                qcm.putExtras(b);
                startActivity(qcm);
                finish();
            }
        });
        retourModePrinc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retourModePrinc = new Intent (ModeDeJeux.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                retourModePrinc.putExtras(b);
                startActivity(retourModePrinc);
                finish();
            }
        });
        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ModeDeJeux.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });


    }

}

