package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listView;

public class AjouterListeClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    ListView listviewajoutliste;
    private String idclasse,idreçu;
    String tableaudelistes[],tableauidliste[],idliste;
    private String tableauidlisteclasse[],tableaudefinitif[];
    PatchList patchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_liste_classe);

        parametres = (ImageButton) findViewById(R.id.parametres);
        retour = (ImageButton) findViewById(R.id.retourarriere);
        listviewajoutliste = (ListView) findViewById(R.id.listviewajoutliste);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
        }


        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (AjouterListeClasse.this, Parametres.class);
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
                Intent versparam= new Intent (AjouterListeClasse.this, Classeduprof.class);
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

        githubService.accederlistedunuser(idreçu,new retrofit.Callback<List<Liste>>() {
            @Override
            public void success(List<Liste> listes, Response response) {
                int lenght = listes.size();
                tableaudelistes = new String[listes.size()];
                tableauidliste = new String[listes.size()];
                for(int i = 0; i<lenght ; i++){
                    tableaudelistes[i] = listes.get(i).getName();
                    tableauidliste[i] = Integer.toString(listes.get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(AjouterListeClasse.this,
                        android.R.layout.simple_list_item_1, tableaudelistes);
                listviewajoutliste.setAdapter(adapterliste);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        listviewajoutliste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idliste = tableauidliste[position];
                if(tableauidlisteclasse!=null) {
                    if (tableauidlisteclasse.length != 0) {
                        tableaudefinitif = new String[tableauidlisteclasse.length + 1];
                        tableaudefinitif[0] = idliste;
                        for (int i = 0; i < tableauidlisteclasse.length; i++) {
                            tableaudefinitif[i + 1] = tableauidlisteclasse[i];
                        }
                    } else {
                        tableaudefinitif = new String[0];
                    }
                }
                patchlist = new PatchList();
                patchlist.setLists(tableaudefinitif);
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                githubService.patchclasseliste(idclasse,patchlist,new retrofit.Callback<Classe>() {
                    @Override
                    public void success(Classe classe, Response response) {
                        Intent versmots = new Intent (AjouterListeClasse.this, Classeduprof.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idclasse",idclasse);
                        b.putString("etat","addlist");
                        versmots.putExtras(b);
                        startActivity(versmots);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        System.out.println(error);
                    }
                });
            }
        });


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
    }
}
