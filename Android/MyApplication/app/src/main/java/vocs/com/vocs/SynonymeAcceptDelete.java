package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.nom;
import static vocs.com.vocs.R.id.non;

public class SynonymeAcceptDelete extends AppCompatActivity {

    ImageButton parametres,retour;
    TextView texteaafficher;
    private String idreçu,idmot,idmotapatch,iddemands,idreceive,idsend,nommotapatch,nommot,nbdemandes,etat;
    Button ouisynonyme,nonsynonyme;
    private int trads[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonyme_accept_delete);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        nonsynonyme = (Button) findViewById(R.id.nonsynonyme);
        ouisynonyme = (Button) findViewById(R.id.ouisynonyme);
        texteaafficher = (TextView) findViewById(R.id.texteaafficher);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idmot=b.getString("idmot");
            idmotapatch=b.getString("idmotapatch");
            nommotapatch=b.getString("nommotapatch");
            nommot=b.getString("nommot");
            iddemands = b.getString("iddemands");
            idreceive = b.getString("idreceive");
            idsend = b.getString("idsend");
            nbdemandes =b.getString("nbdemandes");
            trads = b.getIntArray("trads");
        }

        texteaafficher.setText("Voulez vous faire de "+nommot+" un synonyme de "+nommotapatch+" ?");

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (SynonymeAcceptDelete.this, Parametres.class);
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
                Intent versparam= new Intent (SynonymeAcceptDelete.this, PagePrinc.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        nonsynonyme.setOnClickListener(new View.OnClickListener(){
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
                        Intent versparam= new Intent (SynonymeAcceptDelete.this, GererDemandes.class);
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

        ouisynonyme.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                WordPatch synonyme = new WordPatch();
                synonyme.setContent(nommotapatch);
                synonyme.setLanguage("FR");
                synonyme.setTrads(trads);

                githubService.patchword(idmotapatch,synonyme,new retrofit.Callback<Word>() {
                    @Override
                    public void success(Word word, Response response) {
                        GitService githubService = new RestAdapter.Builder()
                                .setEndpoint(ENDPOINT)
                                .build()
                                .create(GitService.class);

                        githubService.deletedemands(iddemands,new retrofit.Callback<GetDemands>() {
                            @Override
                            public void success(GetDemands demands, Response response) {
                                etat="accept";
                                Intent versparam= new Intent (SynonymeAcceptDelete.this, GererDemandes.class);
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
        });
    }
}
