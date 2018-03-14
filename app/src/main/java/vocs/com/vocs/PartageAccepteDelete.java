package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.string.no;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.accept;
import static vocs.com.vocs.R.id.listvieweleves;
import static vocs.com.vocs.R.id.nom;

import static vocs.com.vocs.R.id.refuserlademande;
import static vocs.com.vocs.R.id.role;

public class PartageAccepteDelete extends AppCompatActivity {

    ImageButton parametres,retour;
    Button accepter,refuser;
    TextView letexte;
    private String idreçu,iddemands,idliste,idreceive,idsend,nbdemandes,nomliste,nomsend,etat;
    private int idlistenew;
    private String namelistenew;
    private int wordTradslistnew[];
    private int test[];
    private int test2[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partage_accepte_delete);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        letexte=(TextView) findViewById(R.id.letexte);
        accepter=(Button) findViewById(R.id.accept);
        refuser= (Button) findViewById(R.id.refuser);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            iddemands = b.getString("iddemands");
            idliste = b.getString("idliste");
            idreceive = b.getString("idreceive");
            idsend = b.getString("idsend");
            nbdemandes =b.getString("nbdemandes");
            nomsend = b.getString("nomsend");
            nomliste=b.getString("nomliste");
        }


        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PartageAccepteDelete.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (PartageAccepteDelete.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        letexte.setText(nomsend+" souhaites vous partager sa liste "+nomliste);

        refuser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                    @Override
                    public void success(GetDemands demands, Response response) {
                        etat="delete";
                        Intent versparam= new Intent (PartageAccepteDelete.this, GererDemandes.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("etat",etat);
                        b.putString("nbdemandes",nbdemandes);
                        versparam.putExtras(b);
                        startActivity(versparam);
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }
        });

        accepter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                githubService.accederliste(idliste,new retrofit.Callback<MotsListe>() {
                    @Override
                    public void success(MotsListe liste, Response response) {
                        int length = liste.getWordTrads().size();
                        wordTradslistnew = new int[length];
                        test = new int[length];
                        for(int i=0;i<liste.getWordTrads().size();i++) {
                            wordTradslistnew[i] = liste.getWordTrads().get(i).getId();
                        }
                        System.out.println(wordTradslistnew[0]);
                        test2=tabwords(wordTradslistnew);
                        System.out.println(test2[0]);
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
                                        GitService githubService = new RestAdapter.Builder()
                                                .setEndpoint(ENDPOINT)
                                                .build()
                                                .create(GitService.class);
                                        githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                                            @Override
                                            public void success(GetDemands demands, Response response) {
                                                etat="accept";
                                                Intent versparam= new Intent (PartageAccepteDelete.this, GererDemandes.class);
                                                Bundle b = new Bundle();
                                                b.putString("id",idreçu);
                                                b.putString("etat",etat);
                                                b.putString("nbdemandes",nbdemandes);
                                                versparam.putExtras(b);
                                                startActivity(versparam);
                                                finish();

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

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });

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
