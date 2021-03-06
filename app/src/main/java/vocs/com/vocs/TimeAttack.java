package vocs.com.vocs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;

import org.w3c.dom.Text;

import java.text.Normalizer;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.id.edit;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.afficheur;
import static vocs.com.vocs.R.id.bienmal;
import static vocs.com.vocs.R.id.bswitch;
import static vocs.com.vocs.R.id.lemot;
import static vocs.com.vocs.R.id.liste;
import static vocs.com.vocs.R.id.progressBar;
import static vocs.com.vocs.R.id.progressBar2;
import static vocs.com.vocs.R.id.soluc;
import static vocs.com.vocs.R.id.textView;

public class TimeAttack extends AppCompatActivity {

    ImageButton coeurplein1,coeurplein2,coeurplein3;
    Button scoreattack,valider;
    EditText editattack;
    TextView textattack,bienmalattack;
    ProgressBar progressbar;
    int nombreMax,nb,bon,tt,longueur;
    private String level[],idList,idreçu,typeliste,tableauidstat[],tableaufrancais[],tableauanglais[],motreponse,motsolution,motaffiche,motsolution2,variabledetest,
            tableaudesynonymes[],badRepetition[],goodRepetition[];
    private WordDansTrads worddanstradsanglais[][],worddanstradsfrancais[][];
    private Handler handler = new Handler();
    int vie=3;
    public int m;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_attack);

        progressbar= (ProgressBar) findViewById(R.id.progressBar2);
        coeurplein1 = (ImageButton) findViewById(R.id.coeurplein1);
        coeurplein2 = (ImageButton) findViewById(R.id.coeurplein2);
        coeurplein3 = (ImageButton) findViewById(R.id.coeurplein3);
        editattack = (EditText) findViewById(R.id.editattack);
        scoreattack = (Button) findViewById(R.id.scoreattack);
        valider = (Button) findViewById(R.id.validerattack);
        textattack = (TextView) findViewById(R.id.textattack);
        bienmalattack = (TextView) findViewById(R.id.bienmalattack);


        progressbar.setProgressTintList(ColorStateList.valueOf(Color.BLUE));

        Bundle b = getIntent().getExtras();

        if (b != null) {
            idList = b.getString("idliste");
            idreçu = b.getString("id");
            typeliste = b.getString("liste");
        }

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
                    worddanstradsanglais = new WordDansTrads[lenght][];
                    worddanstradsfrancais = new WordDansTrads[lenght][];
                    goodRepetition = new String[lenght];
                    badRepetition = new String[lenght];
                    tableauidstat = new String[lenght];
                    level = new String[lenght];
                    for (int i = 0; i < lenght; i++) {
                        if(motliste.getWordTrads().get(i).getWord().getLanguage().getCode().toString().contentEquals("EN")){
                            tableauanglais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                            worddanstradsfrancais[i] = motliste.getWordTrads().get(i).getTrad().getTrads();
                            worddanstradsanglais[i] = motliste.getWordTrads().get(i).getWord().getTrads();
                        }
                        else{
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableauanglais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                            worddanstradsanglais[i] = motliste.getWordTrads().get(i).getTrad().getTrads();
                            worddanstradsfrancais[i] = motliste.getWordTrads().get(i).getWord().getTrads();
                        }
                        goodRepetition[i] = String.valueOf(motliste.getWordTrads().get(i).getStat().getGoodRepetition());
                        badRepetition[i] = String.valueOf(motliste.getWordTrads().get(i).getStat().getBadRepetition());
                        tableauidstat[i] = String.valueOf(motliste.getWordTrads().get(i).getStat().getId());
                        level[i]=String.valueOf(motliste.getWordTrads().get(i).getStat().getLevel());
                    }
                    if (tableaufrancais.length != 0) {
                        bon = 0;
                        tt = 0;
                        nombreMax = tableaufrancais.length;
                        nb = (int) (Math.random() * nombreMax);
                        String motfrancais = String.valueOf(tableaufrancais[nb]);
                        textattack.setText(motfrancais);
                        anim_score();
                        fonction();
                    } else {
                        Intent retour = new Intent(TimeAttack.this, ChoixListeAvantJeux.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        y.putInt("key", 4);
                        y.putString("etat", "true");
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
                    worddanstradsanglais = new WordDansTrads[lenght][];
                    worddanstradsfrancais = new WordDansTrads[lenght][];
                    for (int i = 0; i < lenght; i++) {
                        if(motliste.getWordTrads().get(i).getWord().getLanguage().getCode().toString().contentEquals("EN")){
                            tableauanglais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                            worddanstradsfrancais[i] = motliste.getWordTrads().get(i).getTrad().getTrads();
                            worddanstradsanglais[i] = motliste.getWordTrads().get(i).getWord().getTrads();
                        }
                        else{
                            tableaufrancais[i] = motliste.getWordTrads().get(i).getWord().getContent();
                            tableauanglais[i] = motliste.getWordTrads().get(i).getTrad().getContent();
                            worddanstradsanglais[i] = motliste.getWordTrads().get(i).getTrad().getTrads();
                            worddanstradsfrancais[i] = motliste.getWordTrads().get(i).getWord().getTrads();
                        }
                    }
                    if (tableaufrancais.length != 0) {
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
                                    tableauidstat[u] =String.valueOf(listestat.getWordTrads().get(u).getStat().getId());
                                    level[u]=String.valueOf(listestat.getWordTrads().get(u).getStat().getLevel());
                                }
                                bon = 0;
                                tt = 0;
                                nombreMax = tableaufrancais.length;
                                nb = (int) (Math.random() * nombreMax);
                                String motfrancais = String.valueOf(tableaufrancais[nb]);
                                textattack.setText(motfrancais);
                                anim_score();
                                fonction();

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println(error);
                            }
                        });
                    } else {
                        Intent retour = new Intent(TimeAttack.this, ChoixListeAvantJeux.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        y.putInt("key", 4);
                        y.putString("etat", "true");
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
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }
    public void config(){

        motreponse = editattack.getText().toString();
        motreponse = motreponse.toLowerCase();
        motreponse = removeAccent(motreponse);

    }

    public void anim_score() {
        scoreattack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent score = new Intent(TimeAttack.this, score.class);
                Bundle b = new Bundle();
                Bundle t = new Bundle();
                b.putInt("key", bon);
                t.putInt("autre", tt);
                b.putString("id", idreçu);
                score.putExtras(b);
                score.putExtras(t);
                startActivity(score);
                finish();
            }
        });
    }

    public void fonction() {
            motaffiche = String.valueOf(tableaufrancais[nb]);
            motsolution = String.valueOf(tableauanglais[nb]);
            motsolution2 = motsolution;
            motsolution = motsolution.toLowerCase();
            motsolution = removeAccent(motsolution);
        m=100;
        longueur = motsolution.length();
        new Thread(new Runnable() {
            boolean test;
            public void run() {
                while (m >0) {
                     test = true;
                    m -= 1;

                    handler.post(new Runnable() {
                        public void run() {
                            progressbar.setProgress(m);
                        }
                    });
                    try {
                        Thread.sleep(longueur*20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(m==0){
                    test=false;
                }
                if(!test){
                    Intent score = new Intent(TimeAttack.this, score.class);
                    Bundle b = new Bundle();
                    Bundle t = new Bundle();
                    b.putInt("key", bon);
                    t.putInt("autre", tt);
                    b.putString("id", idreçu);
                    score.putExtras(b);
                    score.putExtras(t);
                    startActivity(score);
                    progressbar.setProgress(0);
                    finish();
                }
            }
        }).start();

        textattack.setText(motaffiche);
        anim_score();

       valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim_score();
                config();
                if(worddanstradsfrancais[nb].length==0){
                    variabledetest="non";
                    System.out.println("non");
                }
                else{
                    variabledetest="oui";
                    System.out.println("oui");
                }
                if(variabledetest.contentEquals("oui")){
                    tableaudesynonymes = new String[worddanstradsfrancais[nb].length];
                    for(int u=0;u<worddanstradsfrancais[nb].length;u++){
                        tableaudesynonymes[u]=worddanstradsfrancais[nb][u].getContent();
                    }
                    for(int bo=0;bo<tableaudesynonymes.length;bo++){
                        System.out.println(motreponse);
                        System.out.println(tableaudesynonymes[bo]);
                        if (tableaudesynonymes[bo].contentEquals(motreponse)) {
                            variabledetest="ok";
                            System.out.println("c'est bon!");
                        }
                    }
                }
                if (motreponse.equals(motsolution)||variabledetest.contentEquals("ok")) {
                    if(variabledetest.contentEquals("ok")){
                        bienmalattack.setText("Bien, mais la meilleure solution était :");
                    }
                    else{
                        succed(nb,goodRepetition,badRepetition,level);
                        PatchStat stats = new PatchStat();
                        stats.setBadRepetition(Integer.valueOf(badRepetition[nb]));
                        stats.setGoodRepetition(Integer.valueOf(goodRepetition[nb]));
                        stats.setLevel(Integer.valueOf(level[nb]));

                        GitService githubService = new RestAdapter.Builder()
                                .setEndpoint(ENDPOINT)
                                .build()
                                .create(GitService.class);

                        githubService.patchstat(tableauidstat[nb],stats, new retrofit.Callback<Stat>() {
                            @Override
                            public void success(Stat stat, Response response) {
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println(error);
                            }
                        });
                        bienmalattack.setText("Bien");
                    }
                    bienmalattack.setTextColor(Color.GREEN);
                    editattack.setText("");
                    bon++;
                    nb=(int) (Math.random()*nombreMax);
                    textattack.setText(motaffiche);
                    progressbar.setProgress(0);
                    fonction();
                }
                else {
                    if(vie==0){
                        Intent score = new Intent(TimeAttack.this, score.class);
                        Bundle b = new Bundle();
                        Bundle t = new Bundle();
                        b.putInt("key", bon);
                        t.putInt("autre", tt);
                        b.putString("id", idreçu);
                        score.putExtras(b);
                        score.putExtras(t);
                        startActivity(score);
                        progressbar.setProgress(0);
                        finish();
                    }
                    else {
                        if(vie==3){
                            coeurplein3.setImageResource(R.drawable.coeur_vide);
                        }
                        else if(vie==2){
                            coeurplein2.setImageResource(R.drawable.coeur_vide);
                        }
                        else{
                            coeurplein1.setImageResource(R.drawable.coeur_vide);
                        }
                        bienmalattack.setText("Pas exactement");
                        failed(nb,goodRepetition,badRepetition,level);
                        PatchStat stats = new PatchStat();
                        stats.setBadRepetition(Integer.valueOf(badRepetition[nb]));
                        stats.setGoodRepetition(Integer.valueOf(goodRepetition[nb]));
                        stats.setLevel(Integer.valueOf(level[nb]));

                        GitService githubService = new RestAdapter.Builder()
                                .setEndpoint(ENDPOINT)
                                .build()
                                .create(GitService.class);

                        githubService.patchstat(tableauidstat[nb],stats, new retrofit.Callback<Stat>() {
                            @Override
                            public void success(Stat stat, Response response) {
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println(error);
                            }
                        });
                        bienmalattack.setTextColor(Color.RED);
                        vie = vie - 1;
                        editattack.setText("");
                        nb = (int) (Math.random() * nombreMax);
                        textattack.setText(motaffiche);
                        progressbar.setProgress(0);
                        fonction();
                    }
                }
                tt++;

            }
        }) ;

    }

    public void succed(int pos,String good[], String bad[],String level[]){
        if (Integer.valueOf(level[pos])<5){
            level[pos]=String.valueOf(Integer.valueOf(level[pos])+1);
        }
        good[pos]=String.valueOf(Integer.valueOf(good[pos])+1);
        if(Integer.valueOf(good[pos])>=2){
            bad[pos]=String.valueOf(0);
        }
    }

    public void failed(int pos,String good[], String bad[],String level[]){
        if (Integer.valueOf(level[pos])>0){
            level[pos]=String.valueOf(Integer.valueOf(level[pos])-1);
        }
        good[pos]=String.valueOf(0);
        bad[pos]=String.valueOf(Integer.valueOf(bad[pos])+1);
    }
}
