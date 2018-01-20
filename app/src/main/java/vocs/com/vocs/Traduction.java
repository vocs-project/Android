package vocs.com.vocs;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
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

import static android.R.attr.id;
import static android.R.attr.x;
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
import static vocs.com.vocs.R.id.role;
import static vocs.com.vocs.R.id.soluc;
import static vocs.com.vocs.R.id.valider;


public class Traduction extends AppCompatActivity {



    int nombreMax, nb;
    String motaffiche,motreponse,motsolution,motsolution2,idreçu,typeliste,idList,motsolution3;
    private String tableaufrancais[],tableauanglais[],role[],roleuserdelaclasse[][],idduprof,variabledetest,tableaudesynonymes[];
    private int iduserdelaclasse[];
    int bon,tt;
    private WordDansTrads worddanstradsanglais[][],worddanstradsfrancais[][];

    TextView afficheur,bienmal,soluc,lemot;
    EditText edit;
    Button valider,aide,score,retour,signaler;
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
        signaler=(Button) findViewById(R.id.signaler);

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
                worddanstradsanglais = new WordDansTrads[lenght][];
                worddanstradsfrancais = new WordDansTrads[lenght][];
                for(int i=0;i<lenght;i++){
                    tableauanglais[i]=motliste.getWordTrads().get(i).getWord().getContent();
                    tableaufrancais[i]=motliste.getWordTrads().get(i).getTrad().getContent();
                    worddanstradsfrancais[i]=motliste.getWordTrads().get(i).getTrad().getTrads();
                    worddanstradsanglais[i]=motliste.getWordTrads().get(i).getWord().getTrads();
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
                Intent retour2 = new Intent (Traduction.this, PagePrinc.class);
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
                            bienmal.setText("Bien, mais la meilleure solution était :");
                        }
                        else{
                            bienmal.setText("Bien");
                        }
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        motsolution3="bien";
                        bon++;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    else {

                        bienmal.setText("Pas exactement");
                        bienmal.setTextColor(Color.RED);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        motsolution3=motaffiche;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }
                    tt++;

                }
            }) ;
            signaler.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                if(motsolution3 !=null && motaffiche!=null && motreponse!=null) {
                    if (!motsolution3.contentEquals("bien")&& !motreponse.contentEquals(" ") && !motreponse.contentEquals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Traduction.this);
                        builder.setTitle("Valider le signalement ?");
                        builder.setMessage(motsolution3 + " - " + motreponse);
                        builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                GitService githubService = new RestAdapter.Builder()
                                        .setEndpoint(ENDPOINT)
                                        .build()
                                        .create(GitService.class);
                                githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                    @Override
                                    public void success(User userrole, Response response) {
                                        int length = userrole.getRoles().length;
                                        role = new String[length];
                                        role = userrole.getRoles();
                                        GitService githubService = new RestAdapter.Builder()
                                                .setEndpoint(ENDPOINT)
                                                .build()
                                                .create(GitService.class);

                                        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                            @Override
                                            public void success(User user, Response response) {
                                                List<Classes> classe = user.getClasses();
                                                if(classe.isEmpty()||role[0].contentEquals("ROLE_PROFESSOR")){
                                                    System.out.println("la");
                                                    DemandeSynonyme demands = new DemandeSynonyme();
                                                    WordSynonymes word = new WordSynonymes();
                                                    WordSynonymes trad = new WordSynonymes();
                                                    WordTraductionSynonymes wordTraduction = new WordTraductionSynonymes();

                                                    word.setLanguage("EN");
                                                    word.setContent(motreponse);

                                                    trad.setLanguage("FR");
                                                    trad.setContent(motsolution3);

                                                    wordTraduction.setTrad(trad);
                                                    wordTraduction.setWord(word);

                                                    demands.setUserSend(Integer.parseInt(idreçu));
                                                    demands.setUserReceive(Integer.parseInt(String.valueOf(48)));
                                                    demands.setWordTrad(wordTraduction);

                                                    GitService githubService = new RestAdapter.Builder()
                                                            .setEndpoint(ENDPOINT)
                                                            .build()
                                                            .create(GitService.class);
                                                    githubService.envoyerdemandesynonyme(demands,new retrofit.Callback<GetDemands>() {
                                                        @Override
                                                        public void success(GetDemands demande, Response response) {
                                                            Toast.makeText(Traduction.this,"demande envoyée",Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void failure(RetrofitError error) {
                                                            System.out.println(error);
                                                        }
                                                    });
                                                }
                                                else{

                                                    GitService githubService = new RestAdapter.Builder()
                                                            .setEndpoint(ENDPOINT)
                                                            .build()
                                                            .create(GitService.class);
                                                    githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                                        @Override
                                                        public void success(User userclasse, Response response) {
                                                            int delaclasse;
                                                            List<Classes> listclasse;
                                                            listclasse = userclasse.getClasses();
                                                            delaclasse = listclasse.get(0).getId();

                                                            GitService githubService = new RestAdapter.Builder()
                                                                    .setEndpoint(ENDPOINT)
                                                                    .build()
                                                                    .create(GitService.class);
                                                            githubService.obtenirclasses(String.valueOf(delaclasse),new retrofit.Callback<Classes>() {
                                                                @Override
                                                                public void success(Classes classesdeuser, Response response) {
                                                                    int lengthuser = classesdeuser.getUsers().size();
                                                                    roleuserdelaclasse = new String[lengthuser][];
                                                                    iduserdelaclasse = new int[lengthuser];

                                                                    for(int i=0;i<lengthuser;i++){
                                                                        iduserdelaclasse[i] = classesdeuser.getUsers().get(i).getId();
                                                                        roleuserdelaclasse[i]=classesdeuser.getUsers().get(i).getRoles();
                                                                    }
                                                                    int length2 = roleuserdelaclasse.length;
                                                                    role = new String[length2];
                                                                    for(int y=0;y<length2;y++){
                                                                        for(int j=0; j<roleuserdelaclasse[y].length; j++) {
                                                                            role[y]=roleuserdelaclasse[y][j];
                                                                        }
                                                                    }
                                                                    for(int u=0;u<role.length;u++){
                                                                        if(role[u].contentEquals("ROLE_PROFESSOR")){
                                                                            idduprof=String.valueOf(iduserdelaclasse[u]);
                                                                        }
                                                                    }
                                                                    DemandeSynonyme demands = new DemandeSynonyme();
                                                                    WordSynonymes word = new WordSynonymes();
                                                                    WordSynonymes trad = new WordSynonymes();
                                                                    WordTraductionSynonymes wordTraduction = new WordTraductionSynonymes();

                                                                    word.setLanguage("EN");
                                                                    word.setContent(motreponse);

                                                                    trad.setLanguage("FR");
                                                                    trad.setContent(motsolution3);

                                                                    wordTraduction.setTrad(trad);
                                                                    wordTraduction.setWord(word);

                                                                    demands.setUserSend(Integer.parseInt(idreçu));
                                                                    demands.setUserReceive(Integer.parseInt(idduprof));
                                                                    demands.setWordTrad(wordTraduction);

                                                                    GitService githubService = new RestAdapter.Builder()
                                                                            .setEndpoint(ENDPOINT)
                                                                            .build()
                                                                            .create(GitService.class);
                                                                    githubService.envoyerdemandesynonyme(demands,new retrofit.Callback<GetDemands>() {
                                                                        @Override
                                                                        public void success(GetDemands demande, Response response) {
                                                                            Toast.makeText(Traduction.this,"demande envoyée",Toast.LENGTH_SHORT).show();
                                                                        }

                                                                        @Override
                                                                        public void failure(RetrofitError error) {
                                                                            System.out.println(error);
                                                                        }
                                                                    });


                                                                }

                                                                @Override
                                                                public void failure(RetrofitError error) {
                                                                    System.out.println(error);
                                                                }
                                                            });

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

                                    @Override
                                    public void failure(RetrofitError error) {
                                        System.out.println(error);
                                    }
                                });

                            }
                        });
                        builder.setNegativeButton("non", null);
                        builder.create();
                        builder.show();
                    }
                }
                }
            });
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
                    if(worddanstradsanglais[nb].length==0){
                        variabledetest="non";
                    }
                    else{
                        variabledetest="oui";
                    }
                    if(variabledetest.contentEquals("oui")){
                        tableaudesynonymes = new String[worddanstradsanglais[nb].length];
                        for(int u=0;u<worddanstradsanglais[nb].length;u++){
                            tableaudesynonymes[u]=worddanstradsanglais[nb][u].getContent();
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
                    if (motreponse.equals(motsolution)||variabledetest.contentEquals("ok")){
                        if( variabledetest.contentEquals("ok")){
                            bienmal.setText("Good, but the best solution is :");
                        }
                        else{
                            bienmal.setText("Good");
                        }
                        bienmal.setTextColor(Color.GREEN);
                        edit.setText("");
                        soluc.setText("");
                        lemot.setText(motsolution2 + " : " + motaffiche);
                        bon++;
                        motsolution3="bien";
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
                        motsolution3=motaffiche;
                        nb=(int) (Math.random()*nombreMax);
                        afficheur.setText(motaffiche);
                        fonction();
                    }tt++;
                    //A mettre apres la prochaine ) pour que ca marche /!\ attention au synonymes
                    signaler.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            if(motsolution3 !=null && motaffiche!=null && motreponse!=null) {
                                if (!motsolution3.contentEquals("bien")&& !motreponse.contentEquals(" ") && !motreponse.contentEquals("")) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Traduction.this);
                                    builder.setTitle("Valider le signalement ?");
                                    builder.setMessage(motsolution3 + " - " + motreponse);
                                    builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            GitService githubService = new RestAdapter.Builder()
                                                    .setEndpoint(ENDPOINT)
                                                    .build()
                                                    .create(GitService.class);
                                            githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                                @Override
                                                public void success(User userrole, Response response) {
                                                    int length = userrole.getRoles().length;
                                                    role = new String[length];
                                                    role = userrole.getRoles();
                                                    GitService githubService = new RestAdapter.Builder()
                                                            .setEndpoint(ENDPOINT)
                                                            .build()
                                                            .create(GitService.class);

                                                    githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                                        @Override
                                                        public void success(User user, Response response) {
                                                            List<Classes> classe = user.getClasses();
                                                            if(classe.isEmpty()||role[0].contentEquals("ROLE_PROFESSOR")){
                                                                System.out.println("la");
                                                                DemandeSynonyme demands = new DemandeSynonyme();
                                                                WordSynonymes word = new WordSynonymes();
                                                                WordSynonymes trad = new WordSynonymes();
                                                                WordTraductionSynonymes wordTraduction = new WordTraductionSynonymes();

                                                                word.setLanguage("EN");
                                                                word.setContent(motreponse);

                                                                trad.setLanguage("FR");
                                                                trad.setContent(motsolution3);

                                                                wordTraduction.setTrad(trad);
                                                                wordTraduction.setWord(word);

                                                                demands.setUserSend(Integer.parseInt(idreçu));
                                                                demands.setUserReceive(Integer.parseInt(String.valueOf(48)));
                                                                demands.setWordTrad(wordTraduction);

                                                                GitService githubService = new RestAdapter.Builder()
                                                                        .setEndpoint(ENDPOINT)
                                                                        .build()
                                                                        .create(GitService.class);
                                                                githubService.envoyerdemandesynonyme(demands,new retrofit.Callback<GetDemands>() {
                                                                    @Override
                                                                    public void success(GetDemands demande, Response response) {
                                                                        Toast.makeText(Traduction.this,"demande envoyée",Toast.LENGTH_SHORT).show();
                                                                    }

                                                                    @Override
                                                                    public void failure(RetrofitError error) {
                                                                        System.out.println(error);
                                                                    }
                                                                });
                                                            }
                                                            else{

                                                                GitService githubService = new RestAdapter.Builder()
                                                                        .setEndpoint(ENDPOINT)
                                                                        .build()
                                                                        .create(GitService.class);
                                                                githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
                                                                    @Override
                                                                    public void success(User userclasse, Response response) {
                                                                        int delaclasse;
                                                                        List<Classes> listclasse;
                                                                        listclasse = userclasse.getClasses();
                                                                        delaclasse = listclasse.get(0).getId();

                                                                        GitService githubService = new RestAdapter.Builder()
                                                                                .setEndpoint(ENDPOINT)
                                                                                .build()
                                                                                .create(GitService.class);
                                                                        githubService.obtenirclasses(String.valueOf(delaclasse),new retrofit.Callback<Classes>() {
                                                                            @Override
                                                                            public void success(Classes classesdeuser, Response response) {
                                                                                int lengthuser = classesdeuser.getUsers().size();
                                                                                roleuserdelaclasse = new String[lengthuser][];
                                                                                iduserdelaclasse = new int[lengthuser];

                                                                                for(int i=0;i<lengthuser;i++){
                                                                                    iduserdelaclasse[i] = classesdeuser.getUsers().get(i).getId();
                                                                                    roleuserdelaclasse[i]=classesdeuser.getUsers().get(i).getRoles();
                                                                                }
                                                                                int length2 = roleuserdelaclasse.length;
                                                                                for(int y=0;y<length2;y++){
                                                                                    for(int j=0; j<roleuserdelaclasse[y].length; j++) {
                                                                                        role[y]=roleuserdelaclasse[y][j];
                                                                                    }
                                                                                }
                                                                                for(int u=0;u<role.length;u++){
                                                                                    if(role[u].contentEquals("ROLE_PROFESSOR")){
                                                                                        idduprof=String.valueOf(iduserdelaclasse[u]);
                                                                                    }
                                                                                }
                                                                                DemandeSynonyme demands = new DemandeSynonyme();
                                                                                WordSynonymes word = new WordSynonymes();
                                                                                WordSynonymes trad = new WordSynonymes();
                                                                                WordTraductionSynonymes wordTraduction = new WordTraductionSynonymes();

                                                                                word.setLanguage("EN");
                                                                                word.setContent(motreponse);

                                                                                trad.setLanguage("FR");
                                                                                trad.setContent(motsolution3);

                                                                                wordTraduction.setTrad(trad);
                                                                                wordTraduction.setWord(word);

                                                                                demands.setUserSend(Integer.parseInt(idreçu));
                                                                                demands.setUserReceive(Integer.parseInt(idduprof));
                                                                                demands.setWordTrad(wordTraduction);

                                                                                GitService githubService = new RestAdapter.Builder()
                                                                                        .setEndpoint(ENDPOINT)
                                                                                        .build()
                                                                                        .create(GitService.class);
                                                                                githubService.envoyerdemandesynonyme(demands,new retrofit.Callback<GetDemands>() {
                                                                                    @Override
                                                                                    public void success(GetDemands demande, Response response) {
                                                                                        Toast.makeText(Traduction.this,"demande envoyée",Toast.LENGTH_SHORT).show();
                                                                                    }

                                                                                    @Override
                                                                                    public void failure(RetrofitError error) {
                                                                                        System.out.println(error);
                                                                                    }
                                                                                });


                                                                            }

                                                                            @Override
                                                                            public void failure(RetrofitError error) {
                                                                                System.out.println(error);
                                                                            }
                                                                        });

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

                                                @Override
                                                public void failure(RetrofitError error) {
                                                    System.out.println(error);
                                                }
                                            });

                                        }
                                    });
                                    builder.setNegativeButton("non", null);
                                    builder.create();
                                    builder.show();
                                }
                            }
                        }
                    });
                }
            });
        }
    }

}

