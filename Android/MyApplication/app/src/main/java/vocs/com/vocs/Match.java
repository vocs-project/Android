package vocs.com.vocs;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import java.util.Arrays;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.lemot;
import static vocs.com.vocs.R.id.motafficheqcm;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.switchqcm;

public class Match extends AppCompatActivity {

    Button mot1,mot2,mot3,mot4,tas,score;
    ImageButton precedant,suivant;
    Switch switchbutton;
    private String idList,idreçu,typeliste;
    private String tableaufrancais[], tableauanglais[],letas[],letasrandom[];
    int nombreMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mot1=(Button) findViewById(R.id.reponse1);
        mot2=(Button) findViewById(R.id.reponse2);
        mot3=(Button) findViewById(R.id.reponse3);
        mot4=(Button) findViewById(R.id.reponse4);
        tas=(Button) findViewById(R.id.tas);
        switchbutton = (Switch) findViewById(R.id.switchbutton);
        score=(Button) findViewById(R.id.scorematch);
        precedant = (ImageButton) findViewById(R.id.precedant);
        suivant = (ImageButton) findViewById(R.id.suivant);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("idliste");
            idreçu = b.getString("id");
            typeliste = b.getString("liste");
        }

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
                if(tableauanglais.length<4){
                    Intent retour = new Intent (Match.this, ChoixListeAvantJeux.class);
                    Bundle y = new Bundle();
                    y.putString("id", idreçu);
                    y.putInt("key",3);
                    y.putString("etat","true");
                    retour.putExtras(y);
                    startActivity(retour);
                    finish();
                }else{
                    Qrandom(1);
                    letas();
                    tas.setText(letasrandom[1]);
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        precedant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                precedant();
            }
        });

        suivant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                suivant();
            }
        });

        tas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                suivant();
            }
        });


    }

    public void function(int i){


    }

    public void Qrandom(int i){

        nombreMax = tableaufrancais.length;
        letas = new String[4];

        int rand1 = (int) (Math.random() * nombreMax);
        int rand2, rand3, rand4;
        do {
            rand2 = (int) (Math.random() * nombreMax);
        } while (rand1 == rand2);
        do {
            rand3 = (int) (Math.random() * nombreMax);
        } while (rand1 == rand3 || rand2 == rand3);
        do {
            rand4 = (int) (Math.random() * nombreMax);
        } while (rand1 == rand4 || rand2 == rand4 || rand3 == rand4);


        if(i == 1) {
            mot1.setText(tableauanglais[rand1]);
            mot2.setText(tableauanglais[rand2]);
            mot3.setText(tableauanglais[rand3]);
            mot4.setText(tableauanglais[rand4]);

            letas[0]=tableaufrancais[rand1];
            letas[1]=tableaufrancais[rand2];
            letas[2]=tableaufrancais[rand3];
            letas[3]=tableaufrancais[rand4];
        }
        else{
            mot1.setText(tableaufrancais[rand1]);
            mot2.setText(tableaufrancais[rand2]);
            mot3.setText(tableaufrancais[rand3]);
            mot4.setText(tableaufrancais[rand4]);

            letas[0]=tableauanglais[rand1];
            letas[1]=tableauanglais[rand2];
            letas[2]=tableauanglais[rand3];
            letas[3]=tableauanglais[rand4];
        }
    }

    public void letas(){
        letasrandom = new String[4];

        int rand1 = (int) (Math.random() * letasrandom.length);
        int rand2, rand3, rand4;
        do {
            rand2 = (int) (Math.random() * letasrandom.length);
        } while (rand1 == rand2);
        do {
            rand3 = (int) (Math.random() * letasrandom.length);
        } while (rand1 == rand3 || rand2 == rand3);
        do {
            rand4 = (int) (Math.random() * letasrandom.length);
        } while (rand1 == rand4 || rand2 == rand4 || rand3 == rand4);

        letasrandom[rand1]=letas[0];
        letasrandom[rand2]=letas[1];
        letasrandom[rand3]=letas[2];
        letasrandom[rand4]=letas[3];
    }

    public void suivant(){
       if(Arrays.asList(letasrandom).indexOf(tas.getText().toString()) == letasrandom.length-1){
           tas.setText(letasrandom[0]);
       }
        else{
           tas.setText(letasrandom[Arrays.asList(letasrandom).indexOf(tas.getText().toString())+1]);
       }
    }

    public void precedant(){
        if(Arrays.asList(letasrandom).indexOf(tas.getText().toString()) == 0){
            tas.setText(letasrandom[letasrandom.length-1]);
        }
        else{
            tas.setText(letasrandom[Arrays.asList(letasrandom).indexOf(tas.getText().toString())-1]);
        }
    }
}
