package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.nom;
import static vocs.com.vocs.R.id.nomclasse;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retour;

public class DeleteListeClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idliste,idreçu,idclasse,tableauidlisteclasse[],tableaudefinitif[];
    Button oui,non;
    TextView nomliste;
    PatchList patchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_liste_classe);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        nomliste = (TextView) findViewById(R.id.nomliste);
        oui = (Button) findViewById(R.id.oui);
        non = (Button) findViewById(R.id.non);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            idliste = b.getString("idliste");
        }
        System.out.println(idclasse);

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DeleteListeClasse.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        non.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DeleteListeClasse.this, ListesDuneClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                b.putString("etat","non");
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        retour.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (DeleteListeClasse.this, ListesDuneClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.obtenirclasses(idclasse,new retrofit.Callback<Classes>() {
            @Override
            public void success(Classes classes, Response response) {
                int lenght = classes.getLists().size();
                tableauidlisteclasse = new String[lenght];
                for(int i=0;i<lenght;i++){
                    tableauidlisteclasse[i]=Integer.toString(classes.getLists().get(i).getId());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        githubService.accederliste(idliste,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motliste, Response response) {
               nomliste.setText(motliste.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        oui.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(tableauidlisteclasse!=null) {
                    if (tableauidlisteclasse.length != 0) {
                        tableaudefinitif = new String[tableauidlisteclasse.length - 1];
                        int n = 0;
                        for (int i = 0; i < tableauidlisteclasse.length; i++) {
                            if (tableauidlisteclasse[i].contentEquals(idliste)) {
                                for(int y=0;y<tableaudefinitif.length-n;y++){
                                    tableaudefinitif[n] = tableauidlisteclasse[n + 1];
                                }
                            }
                            else {
                                tableaudefinitif[i] = tableauidlisteclasse[i];
                                n++;
                            }
                        }
                    } else {
                        tableaudefinitif = new String[0];
                    }
                }
                System.out.println(tableauidlisteclasse[0]);
//                System.out.println(tableaudefinitif[0]);
                patchlist = new PatchList();
                patchlist.setLists(tableaudefinitif);
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                githubService.patchclasseliste(idclasse,patchlist,new retrofit.Callback<Classe>() {
                    @Override
                    public void success(Classe classe, Response response) {
                        Intent versparam= new Intent (DeleteListeClasse.this, ListesDuneClasse.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idclasse",idclasse);
                        b.putString("etat","oui");
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
    }
}
