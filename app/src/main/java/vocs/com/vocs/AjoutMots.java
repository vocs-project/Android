package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.value;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.btnAdd;
import static vocs.com.vocs.R.id.editText;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.retours;

public class AjoutMots extends AppCompatActivity {


    Button ajout_mot;
    EditText editanglais,editfrancais;
    String idreçu,idliste;
    ImageButton retour_mots, param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_mots);

        editanglais= (EditText) findViewById(R.id.editanglais);
        editfrancais= (EditText) findViewById(R.id.editfrancais);
        retour_mots = (ImageButton) findViewById(R.id.retourarriere);
        ajout_mot = (Button) findViewById(R.id.ajout_mot);
        param=(ImageButton) findViewById(R.id.parametres);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
        }
        param.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam = new Intent (AjoutMots.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
        retour_mots.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versliste = new Intent (AjoutMots.this, Mots.class);
                Bundle b = new Bundle();
                b.putString("idliste",idliste);
                b.putString("id",idreçu);
                versliste.putExtras(b);
                startActivity(versliste);
                finish();
            }
        });
        ajout_mot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String motanglais=editanglais.getText().toString();
                String motfrançais=editfrancais.getText().toString();

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                Postword word = new Postword();
                Postword trad = new Postword();

                word.setContent(motanglais);
                trad.setContent(motfrançais);

                word.setLanguage("EN");
                trad.setLanguage("FR");

                Wordpost wordpost = new Wordpost();
                wordpost.setTrad(trad);
                wordpost.setWord(word);

                githubService.ajoutermotdanslisteuser(idreçu,idliste,wordpost,new retrofit.Callback<Wordpost>() {
                    @Override
                    public void success(Wordpost word, Response response) {
                        Intent versliste = new Intent (AjoutMots.this, Mots.class);
                        Bundle b = new Bundle();
                        b.putString("idliste",idliste);
                        b.putString("id",idreçu);
                        versliste.putExtras(b);
                        startActivity(versliste);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(AjoutMots.this, "erreur", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
