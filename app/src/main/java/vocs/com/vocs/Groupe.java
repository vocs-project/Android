package vocs.com.vocs;

import android.content.DialogInterface;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.BottomBar;
import static vocs.com.vocs.R.id.listview;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retourarriere;

public class Groupe extends AppCompatActivity {

    String idreçu;
    private String idclasse;
    String[] roles;
    String classes[],tableauliste[],tableauidliste[];
    ImageButton parametres, retourarriere;
    BottomNavigationView BottomBar;
    TextView nom,email,role,classe,nbliste;
    Button intégrerclasse,quitter,voirclasse,profil;
    private int wordTradslistnew[];
    private int test[];
    private int test2[];
    private int idlistenew,tailleliste;
    private String namelistenew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);

        nom = (TextView) findViewById(R.id.nomprenom);
        email = (TextView) findViewById(R.id.email);
        role = (TextView) findViewById(R.id.role);
        classe = (TextView) findViewById(R.id.classe);
        nbliste = (TextView) findViewById(R.id.nblistes);
        BottomBar = (BottomNavigationView) findViewById(R.id.BottomBar);
        parametres = (ImageButton) findViewById(R.id.parametress);
        retourarriere = (ImageButton) findViewById(R.id.retourarrieredegroupe);
        intégrerclasse = (Button) findViewById(R.id.intégrerclasse);
        quitter = (Button) findViewById(R.id.quitter);
        voirclasse = (Button) findViewById(R.id.voirclasse);
        profil = (Button) findViewById(R.id.profil);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
        }

        BottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.legroupe:
                        Intent groupe = new Intent (Groupe.this,SavoirRole.class);
                        Bundle y = new Bundle();
                        y.putString("id", idreçu);
                        groupe.putExtras(y);
                        startActivity(groupe);
                        finish();
                        break;

                    case R.id.lamanette:
                        Intent modeJeux = new Intent (Groupe.this, ModeDeJeux.class);
                        Bundle d = new Bundle();
                        d.putString("id", idreçu);
                        System.out.println(idreçu);
                        modeJeux.putExtras(d);
                        startActivity(modeJeux);
                        finish();
                        break;

                    case R.id.laliste:
                        Intent versliste = new Intent (Groupe.this, ViewListContents.class);
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

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Groupe.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederaunuser(idreçu,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                nom.setText(user.getFirstandSur());
                email.setText(user.getEmail());
                roles = user.getRoles();
                classes = new String[user.getClasses().size()];
                int lenght = user.getClasses().size();
                int taille = user.getListes().size();
                nbliste.setText("Vous possédez "+taille+" listes");
                for(int i=0; i<lenght;i++){
                    classes[i]  = user.getClasses().get(i).getName();
                    classe.setText("Vous faites partis de "+classes[0]);
                    idclasse  =String.valueOf(user.getClasses().get(i).getId());
                    System.out.println(idclasse);
                }
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                System.out.println(idclasse);
                githubService.obtenirclasses(idclasse,new retrofit.Callback<Classes>() {
                    @Override
                    public void success(Classes classe, Response response) {
                        int taille = classe.getLists().size();
                        if (taille != 0) {
                            tableauliste = new String[taille];
                            tableauidliste = new String[taille];
                            for (int i = 0; i < taille; i++) {
                                tableauliste[i] = classe.getLists().get(i).getName();
                                tableauidliste[i] = Integer.toString(classe.getLists().get(i).getId());
                            }
                            tailleliste = classe.getLists().size();
                        }
                    }
                        @Override
                        public void failure(RetrofitError error) {
                            System.out.println(error);
                        }
                    });

                if(user.getClasses().size()>0) {
                    idclasse = Integer.toString(user.getClasses().get(0).getId());
                    voirclasse.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            Intent vers= new Intent (Groupe.this, VoirSaClasse.class);
                            Bundle b = new Bundle();
                            b.putString("id",idreçu);
                            b.putString("idclasse",idclasse);
                            vers.putExtras(b);
                            startActivity(vers);
                            finish();
                        }
                    });
                }
                role.setText("Vous êtes un utilisateur");
                if(roles[0].contentEquals("ROLE_STUDENT")){
                    role.setText("Vous êtes un étudiant");
                }
                if(roles[0].contentEquals("ROLE_PROFESSOR")){
                    role.setText("Vous êtes un professeur");
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        intégrerclasse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versintegr= new Intent (Groupe.this, Integrerclasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versintegr.putExtras(b);
                startActivity(versintegr);
                finish();
            }
        });

        retourarriere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versretour= new Intent (Groupe.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });

        quitter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(idclasse!=null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Groupe.this);
                    builder.setTitle("Voulez vous garder les listes de cette classe ?");
                    builder.setPositiveButton("oui",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i=0;i<tableauidliste.length;i++){
                                String idliste = tableauidliste[i];
                                GitService githubService = new RestAdapter.Builder()
                                        .setEndpoint(ENDPOINT)
                                        .build()
                                        .create(GitService.class);
                                githubService.accederliste(idliste,new retrofit.Callback<MotsListe>() {
                                    @Override
                                    public void success(MotsListe liste, Response response) {
                                        int length = liste.getWordTrads().size();
                                        System.out.println(length);
                                        wordTradslistnew = new int[length];
                                        test = new int[length];
                                        if(length!=0) {
                                            for (int i = 0; i < liste.getWordTrads().size(); i++) {
                                                wordTradslistnew[i] = liste.getWordTrads().get(i).getId();
                                            }
                                        }
                                        test2=tabwords(wordTradslistnew);
                                        namelistenew=liste.getName();

                                        Listepost listepost = new Listepost();
                                        listepost.setName(namelistenew);

                                        GitService githubService = new RestAdapter.Builder()
                                                .setEndpoint(ENDPOINT)
                                                .build()
                                                .create(GitService.class);
                                        githubService.ajouterliste(idreçu,listepost,new retrofit.Callback<Liste>() {
                                            @Override
                                            public void success(Liste liste, Response response) {
                                                idlistenew=liste.getId();

                                                ListPatchWord listpatchword = new ListPatchWord();
                                                listpatchword.setWordTrads(wordTradslistnew);
                                                GitService githubService = new RestAdapter.Builder()
                                                        .setEndpoint(ENDPOINT)
                                                        .build()
                                                        .create(GitService.class);
                                                githubService.patchwordliste(String.valueOf(idlistenew),listpatchword,new retrofit.Callback<Liste>() {
                                                    @Override
                                                    public void success(Liste liste, Response response) {

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

                                githubService.deleteuserdeclasse(idclasse, idreçu, new retrofit.Callback<Classe>() {
                                    @Override
                                    public void success(Classe classe, Response response) {
                                        Toast.makeText(Groupe.this, "Vous n'avez plus de classe", Toast.LENGTH_SHORT).show();
                                        Intent versretour = new Intent(Groupe.this, Groupe.class);
                                        Bundle b = new Bundle();
                                        b.putString("id", idreçu);
                                        versretour.putExtras(b);
                                        startActivity(versretour);
                                        finish();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        System.out.println(error);
                                    }
                                });
                            }
                        }
                    });
                    builder.setNegativeButton("non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GitService githubService = new RestAdapter.Builder()
                                    .setEndpoint(ENDPOINT)
                                    .build()
                                    .create(GitService.class);
                            githubService.deleteuserdeclasse(idclasse, idreçu, new retrofit.Callback<Classe>() {
                                @Override
                                public void success(Classe classe, Response response) {
                                    Toast.makeText(Groupe.this, "Vous n'avez plus de classe", Toast.LENGTH_SHORT).show();
                                    Intent versretour = new Intent(Groupe.this, Groupe.class);
                                    Bundle b = new Bundle();
                                    b.putString("id", idreçu);
                                    versretour.putExtras(b);
                                    startActivity(versretour);
                                    finish();
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    System.out.println(error);
                                }
                            });
                        }
                    });
                    builder.create();
                    builder.show();
                }
                else{
                    Toast.makeText(Groupe.this, "Vous n'avez pas de classe", Toast.LENGTH_SHORT).show();
                }

            }
        });

        profil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versprofil= new Intent (Groupe.this, ChangerProfil.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("type","groupe");
                versprofil.putExtras(b);
                startActivity(versprofil);
                finish();
            }
        });

    }
    public int[] tabwords (int tableau[]){
        int length = tableau.length;
        test = new int[length];
        for(int i=0;i<length;i++){
            test[i]=tableau[i];
        }
        return test;
    }


}
