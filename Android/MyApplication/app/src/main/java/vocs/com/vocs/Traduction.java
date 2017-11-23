package vocs.com.vocs;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.id.edit;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.drawable.groupe;
import static vocs.com.vocs.R.drawable.liste;
import static vocs.com.vocs.R.id.TraductionNormale;
import static vocs.com.vocs.R.id.aide;
import static vocs.com.vocs.R.id.bienmal;
import static vocs.com.vocs.R.id.bswitch;
import static vocs.com.vocs.R.id.lemot;
import static vocs.com.vocs.R.id.listviewclasseprof;
import static vocs.com.vocs.R.id.soluc;
import static vocs.com.vocs.R.id.valider;


public class Traduction extends AppCompatActivity {



    int nombreMax, nb;
    String motaffiche,motreponse,motsolution,motsolution2,idreçu,typeliste,idList;
    private String tableaufrancais[],tableauanglais[];
    int bon,tt;

    TextView afficheur,bienmal,soluc,lemot;
    EditText edit;
    Button valider,aide,score,retour;
    Switch bswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduction);


        afficheur = (TextView) findViewById(R.id.afficheur);
        bienmal = (TextView) findViewById(R.id.bienmal);
        retour = (Button) findViewById(R.id.retour);
        soluc= (TextView) findViewById(R.id.soluc);
        lemot=(TextView) findViewById(R.id.lemot);
        edit=(EditText) findViewById(R.id.reponse);
        valider=(Button) findViewById(R.id.valider);
        aide=(Button) findViewById(R.id.aide);
        score=(Button) findViewById(R.id.score);
        bswitch=(Switch) findViewById(R.id.bswitch);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("idliste");
            idreçu = b.getString("id");
            typeliste = b.getString("liste");
        }

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verschoixliste = new Intent(Traduction.this, ChoixListe.class);
                Bundle y = new Bundle();
                y.putString("id", idreçu);
                verschoixliste.putExtras(y);
                startActivity(verschoixliste);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederliste(idList,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motliste, Response response) {
                int lenght = motliste.getWordTrads().size();
                tableaufrancais = new String[lenght];
                tableauanglais = new String[lenght];
                for(int i=0;i<lenght;i++){
                    tableauanglais[i]=motliste.getWordTrads().get(i).getWord().getContent();
                    tableaufrancais[i]=motliste.getWordTrads().get(i).getTrad().getContent();
                }
                if(tableaufrancais.length != 0) {
                    bon = 0;
                    tt = 0;
                    nombreMax = tableaufrancais.length;
                    nb = (int) (Math.random() * nombreMax);
                    String motfrancais = String.valueOf(tableaufrancais[nb]);
                    afficheur.setText(motfrancais);
                    anim_score();
                    fonction();
                }
                else{
                    Intent retour = new Intent (Traduction.this, ChoixListeAvantJeux.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    y.putInt("key",1);
                    y.putString("etat","true");
                    retour.putExtras(y);
                    startActivity(retour);
                    finish();
                }

            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }
    public void config(){

        motreponse = edit.getText().toString();
        motreponse = motreponse.toLowerCase();
        motreponse = removeAccent(motreponse);

    }
    public void anim_score(){
        score.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent score = new Intent(Traduction.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                t.putInt("autre", tt);
                b.putString("id",idreçu);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
        });
        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent retour2 = new Intent (Traduction.this, ChoixListeAvantJeux.class);
                Bundle y = new Bundle();
                y.putString("id", idreçu);
                y.putInt("key",1);
                retour2.putExtras(y);
                startActivity(retour2);
                finish();
            }
        });
    }
    public void maide(){
        aide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                soluc.setText(motsolution);
            }
        });

    }
    public void fonction(){
        if (bswitch.isChecked()){

            motaffiche = String.valueOf(tableaufrancais[nb]);
            motsolution = String.valueOf(tableauanglais[nb]);
            motsolution2=motsolution;
            motsolution = motsolution.toLowerCase();
            motsolution = removeAccent(motsolution);

            afficheur.setText(motaffiche);
            anim_score();
            maide();
            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)) {
                        bienmal.setText("Bien");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    } else {

                        bienmal.setText("Pas exactement");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;

                }
            }) ;
        }
        else {

            motaffiche = String.valueOf(tableauanglais[nb]);
            motsolution = String.valueOf(tableaufrancais[nb]);
            motsolution2=motsolution;
            motsolution = motsolution.toLowerCase();
            motsolution = removeAccent(motsolution);
            anim_score();
            config();
            afficheur.setText(motaffiche);
            maide();
            valider.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    anim_score();
                    config();
                    if (motreponse.equals(motsolution)){
                        bienmal.setText("Good");
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        bon++;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    else{
                        bienmal.setText("Not really");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;
                }
            });
        }
    }

}

