package vocs.com.vocs;


import android.content.Intent;
import android.graphics.Color;
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
import static vocs.com.vocs.R.id.liste;
import static vocs.com.vocs.R.id.motafficheqcm;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.scoreqcm;
import static vocs.com.vocs.R.id.switchqcm;

public class Match extends AppCompatActivity {

    Button mot1,mot2,mot3,mot4,tas,score,validermatch;
    ImageButton precedant,suivant;
    Switch switchbutton;
    private String idList,idreçu,typeliste;
    private String level[],tableauidstat[],tableaufrancais[], tableauanglais[],tableausoluc[],letas[],letasrandom[],goodRepetition[],badRepetition[];
    int nombreMax,mémoiretasrand1,mémoiretasrand2,mémoiretasrand3,mémoiretasrand4,positiontas,bon,tt;
    boolean etat1,etat2,etat3,etat4;
    String recup1[],recup2[],recup3[],recup4[];


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
        validermatch=(Button) findViewById(R.id.validermatch);

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("idliste");
            idreçu = b.getString("id");
            typeliste = b.getString("liste");
        }
        bon=0;
        tt=0;

        if(typeliste.contentEquals("hard")){
            GitService githubService = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .build()
                    .create(GitService.class);

            githubService.hardlist(idreçu, new retrofit.Callback<MotsListe>() {
                @Override
                public void success(MotsListe motliste, Response response) {
                    int lenght = motliste.getWordTrads().size();
                    tableaufrancais = new String[lenght];
                    tableauanglais = new String[lenght];
                    tableausoluc = new String[lenght];
                    goodRepetition = new String[lenght];
                    badRepetition = new String[lenght];
                    tableauidstat = new String[lenght];
                    level = new String[lenght];
                    for (int i = 0; i < lenght; i++) {
                        if(motliste.getWordTrads().get(i).getWord().getLanguage().getCode().toString().contentEquals("EN")){
                            tableauanglais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                        }
                        else{
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableauanglais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                        }
                        goodRepetition[i]=String.valueOf(motliste.getWordTrads().get(i).getStat().getGoodRepetition());
                        badRepetition[i]=String.valueOf(motliste.getWordTrads().get(i).getStat().getBadRepetition());
                        tableauidstat[i]=String.valueOf(motliste.getWordTrads().get(i).getStat().getId());
                        level[i]=String.valueOf(motliste.getWordTrads().get(i).getStat().getLevel());
                    }
                    if (tableauanglais.length < 4) {
                        Intent retour = new Intent(Match.this, ChoixListeAvantJeux.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        y.putInt("key", 3);
                        y.putString("etat", "true");
                        retour.putExtras(y);
                        startActivity(retour);
                        finish();
                    } else {
                        anim_score();
                        mot1.setBackgroundColor(Color.GRAY);
                        mot2.setBackgroundColor(Color.GRAY);
                        mot3.setBackgroundColor(Color.GRAY);
                        mot4.setBackgroundColor(Color.GRAY);
                        Qrandom(1);
                        letas();
                        positiontas = 0;
                        tas.setText(letasrandom[0]);
                        recup1 = new String[3];
                        recup2 = new String[3];
                        recup3 = new String[3];
                        recup4 = new String[3];
                        function();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error);
                }
            });

        }
        else {
            GitService githubService = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .build()
                    .create(GitService.class);

            githubService.accederliste(idList, new retrofit.Callback<MotsListe>() {
                @Override
                public void success(MotsListe motliste, Response response) {
                    final int lenght = motliste.getWordTrads().size();
                    tableaufrancais = new String[lenght];
                    tableauanglais = new String[lenght];
                    tableausoluc = new String[lenght];
                    for (int i = 0; i < lenght; i++) {
                        if(motliste.getWordTrads().get(i).getWord().getLanguage().getCode().toString().contentEquals("EN")){
                            tableauanglais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                        }
                        else{
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableauanglais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                        }
                    }
                    if (tableauanglais.length < 4) {
                        Intent retour = new Intent(Match.this, ChoixListeAvantJeux.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        y.putInt("key", 3);
                        y.putString("etat", "true");
                        retour.putExtras(y);
                        startActivity(retour);
                        finish();
                    } else {
                        GitService githubService = new RestAdapter.Builder()
                                .setEndpoint(ENDPOINT)
                                .build()
                                .create(GitService.class);

                        githubService.recupstat(idreçu,idList, new retrofit.Callback<ListeTout>() {
                            @Override
                            public void success(ListeTout listestat, Response response) {
                                goodRepetition = new String[lenght];
                                badRepetition = new String[lenght];
                                tableauidstat = new String[lenght];
                                level = new String[lenght];
                                for(int u=0;u<lenght;u++){
                                    goodRepetition[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getGoodRepetition());
                                    badRepetition[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getBadRepetition());
                                    tableauidstat[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getId());
                                    level[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getLevel());
                                }
                                anim_score();
                                mot1.setBackgroundColor(Color.GRAY);
                                mot2.setBackgroundColor(Color.GRAY);
                                mot3.setBackgroundColor(Color.GRAY);
                                mot4.setBackgroundColor(Color.GRAY);
                                Qrandom(1);
                                letas();
                                positiontas = 0;
                                tas.setText(letasrandom[0]);
                                recup1 = new String[3];
                                recup2 = new String[3];
                                recup3 = new String[3];
                                recup4 = new String[3];
                                function();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println(error);
                            }
                        });
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error);
                }
            });
        }

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

    public void function(){

        etat1=false;
        etat2=false;
        etat3=false;
        etat4=false;
        anim_score();
            mot1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etat1 != true) {
                        recup1[0] = mot1.getText().toString();
                        recup1[1] = tas.getText().toString();
                        mot1.setText(mot1.getText() + " - " + tas.getText());
                        recup1[2] = letas[0];
                        for (int i = 0; i < letasrandom.length; i++) {
                            if (letasrandom[i].equals(recup1[1])) {
                                letasrandom[i] = " ";
                                mémoiretasrand1 = i;
                            }
                        }
                        suivant();
                        etat1 = true;
                    } else {
                        mot1.setText(recup1[0]);
                        letasrandom[mémoiretasrand1] = recup1[1];
                        suivant();
                        etat1 = false;
                    }
                }
            });

            mot2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etat2 != true) {
                        recup2[0] = mot2.getText().toString();
                        recup2[1] = tas.getText().toString();
                        mot2.setText(mot2.getText() + " - " + tas.getText());
                        recup2[2] = letas[1];
                        for (int i = 0; i < letasrandom.length; i++) {
                            if (letasrandom[i].equals(recup2[1])) {
                                letasrandom[i] = " ";
                                mémoiretasrand2 = i;
                            }
                        }
                        suivant();
                        etat2 = true;
                    } else {
                        mot2.setText(recup2[0]);
                        letasrandom[mémoiretasrand2] = recup2[1];
                        suivant();
                        etat2 = false;
                    }
                }
            });

            mot3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etat3 != true) {
                        recup3[0] = mot3.getText().toString();
                        recup3[1] = tas.getText().toString();
                        mot3.setText(mot3.getText() + " - " + tas.getText());
                        recup3[2] = letas[2];
                        for (int i = 0; i < letasrandom.length; i++) {
                            if (letasrandom[i].equals(recup3[1])) {
                                letasrandom[i] = " ";
                                mémoiretasrand3 = i;
                            }
                        }
                        suivant();
                        etat3 = true;
                    } else {
                        mot3.setText(recup3[0]);
                        letasrandom[mémoiretasrand3] = recup3[1];
                        suivant();
                        etat3 = false;
                    }
                }
            });

            mot4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etat4 != true) {
                        recup4[0] = mot4.getText().toString();
                        recup4[1] = tas.getText().toString();
                        mot4.setText(mot4.getText() + " - " + tas.getText());
                        recup4[2] = letas[3];
                        for (int i = 0; i < letasrandom.length; i++) {
                            if (letasrandom[i].equals(recup4[1])) {
                                letasrandom[i] = " ";
                                mémoiretasrand4 = i;
                            }
                        }
                        suivant();
                        etat4 = true;
                    } else {
                        mot4.setText(recup4[0]);
                        letasrandom[mémoiretasrand4] = recup4[1];
                        suivant();
                        etat4 = false;
                    }
                }
            });

        validermatch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                tt=tt+4;
                anim_score();
                if(tableausoluc[0].equals(recup1[1])&&tableausoluc[1].equals(recup2[1])&&tableausoluc[2].equals(recup3[1])&&tableausoluc[3].equals(recup4[1])){
                    mot1.setBackgroundColor(Color.GREEN);
                    mot2.setBackgroundColor(Color.GREEN);
                    mot3.setBackgroundColor(Color.GREEN);
                    mot4.setBackgroundColor(Color.GREEN);
                    bon=bon+4;
                }
                else{
                    if(!tableausoluc[0].equals(recup1[1])){
                        mot1.setBackgroundColor(Color.RED);
                        mot1.setText(recup1[0]+" - "+tableausoluc[0]);
                    }
                    else{
                        mot1.setBackgroundColor(Color.GREEN);
                        bon=bon+1;
                    }
                    if(!tableausoluc[1].equals(recup2[1])){
                        mot2.setBackgroundColor(Color.RED);
                        mot2.setText(recup2[0]+" - "+tableausoluc[1]);
                    }
                    else{
                        mot2.setBackgroundColor(Color.GREEN);
                        bon=bon+1;
                    }
                    if(!tableausoluc[2].equals(recup3[1])){
                        mot3.setBackgroundColor(Color.RED);
                        mot3.setText(recup3[0]+" - "+tableausoluc[2]);
                    }
                    else{
                        mot3.setBackgroundColor(Color.GREEN);
                        bon=bon+1;
                    }
                    if(!tableausoluc[3].equals(recup4[1])){
                        mot4.setBackgroundColor(Color.RED);
                        mot4.setText(recup4[0]+" - "+tableausoluc[3]);
                    }
                    else{
                        mot4.setBackgroundColor(Color.GREEN);
                        bon=bon+1;
                    }
                }
                validermatch.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        mot1.setBackgroundColor(Color.GRAY);
                        mot2.setBackgroundColor(Color.GRAY);
                        mot3.setBackgroundColor(Color.GRAY);
                        mot4.setBackgroundColor(Color.GRAY);
                        recup1 = new String[3];
                        recup2 = new String[3];
                        recup3 = new String[3];
                        recup4 = new String[3];
                        if(switchbutton.isChecked()){
                            Qrandom(2);
                        }else {
                            Qrandom(1);
                        }
                        letas();
                        positiontas = 0;
                        tas.setText(letasrandom[0]);
                        function();
                    }
                });
                anim_score();
            }
        });
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

            tableausoluc[0]=tableaufrancais[rand1];
            tableausoluc[1]=tableaufrancais[rand2];
            tableausoluc[2]=tableaufrancais[rand3];
            tableausoluc[3]=tableaufrancais[rand4];
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

            tableausoluc[0]=tableauanglais[rand1];
            tableausoluc[1]=tableauanglais[rand2];
            tableausoluc[2]=tableauanglais[rand3];
            tableausoluc[3]=tableauanglais[rand4];
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
        if( positiontas == 3){
           tas.setText(letasrandom[0]);
            positiontas = 0;
       }
       else if( positiontas == 2){
           tas.setText(letasrandom[3]);
           positiontas = positiontas+1;
       }
       else if( positiontas == 1){
           tas.setText(letasrandom[2]);
           positiontas = positiontas+1;
       }
       else if( positiontas == 0){
           tas.setText(letasrandom[1]);
           positiontas = positiontas+1;
       }
    }

    public void precedant(){
        if(positiontas == 0){
            tas.setText(letasrandom[3]);
            positiontas = 3;
        }
        else if(positiontas == 3){
            tas.setText(letasrandom[2]);
            positiontas = 2;
        }
        else if(positiontas == 2){
            tas.setText(letasrandom[1]);
            positiontas = 1;
        }
        else if(positiontas == 1){
            tas.setText(letasrandom[0]);
            positiontas = 0;
        }

    }

    public void anim_score() {
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(Match.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                b.putString("id",idreçu);
                t.putInt("autre", tt);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
        });
    }
}
