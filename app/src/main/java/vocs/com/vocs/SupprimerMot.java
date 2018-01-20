package vocs.com.vocs;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.leedit;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.retours;

public class SupprimerMot extends AppCompatActivity {

    ImageButton parametres,retourmot2,hp;
    BottomNavigationView BottomBar;
    Button supprimerok;
    String idreçu,idliste,word,idword,wordanglais;
    TextView mot;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_mot);

        retourmot2=(ImageButton) findViewById(R.id.retourmot2);
        supprimerok=(Button) findViewById(R.id.supprimerok);
        parametres=(ImageButton) findViewById(R.id.parametres);
        BottomBar=(BottomNavigationView) findViewById(R.id.BottomBar);
        mot = (TextView) findViewById(R.id.motaffichés);
        hp = (ImageButton) findViewById(R.id.hp);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
            idword = b.getString("idword");
            word = b.getString("word");
            wordanglais = b.getString("wordanglais");
        }
        mot.setText(word);


        retourmot2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (SupprimerMot.this, ViewListContents.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });

        hp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                textToSpeech.speak(wordanglais, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (SupprimerMot.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (SupprimerMot.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (SupprimerMot.this, ViewListContents.class);
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

        supprimerok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GitService githubService = new RestAdapter.Builder()
                            .setEndpoint(ENDPOINT)
                            .build()
                            .create(GitService.class);
                    System.out.println(idreçu);
                    githubService.deletemot(idliste, idword, new retrofit.Callback<MotsListe>() {
                        @Override
                        public void success(MotsListe motslistes, Response response) {
                            Intent versliste = new Intent (SupprimerMot.this, ViewListContents.class);
                            Bundle b = new Bundle();
                            b.putString("id",idreçu);
                            b.putString("idliste",idliste);
                            versliste.putExtras(b);
                            startActivity(versliste);
                            finish();
                        }

                        @Override
                        public void failure(RetrofitError error) {

                            Toast.makeText(SupprimerMot.this, "erreur", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        });

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (SupprimerMot.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });



    }
}
