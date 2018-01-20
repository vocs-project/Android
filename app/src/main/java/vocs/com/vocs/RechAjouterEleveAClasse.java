package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.id.list;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasses;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retour;

public class RechAjouterEleveAClasse extends AppCompatActivity {

    ImageButton parametres,retour,loupe;
    EditText recherche;
    ListView listes;
    private String idreçu,idclasse,tableauusername[],tableauuserid[],textrecherche,tableautrié[],tableautriéid[],iduser;
    int longueur;
    String etat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rech_ajouter_eleve_aclasse);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        listes = (ListView) findViewById(R.id.list);
        recherche = (EditText) findViewById(R.id.recherche);
        loupe = (ImageButton) findViewById(R.id.loupe);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            etat = b.getString("etat");
        }
        if(etat!=null){
            if(etat.contentEquals("envoie")){
                Toast.makeText(RechAjouterEleveAClasse.this,"Demande envoyée",Toast.LENGTH_SHORT).show();
            }
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (RechAjouterEleveAClasse.this, Parametres.class);
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
                Intent versparam= new Intent (RechAjouterEleveAClasse.this, Classeduprof.class);
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

        githubService.accederauxusers(new retrofit.Callback<List<User>>() {
            @Override
            public void success(List<User> user, Response response) {
                final int lenght = user.size();
                tableauusername = new String[lenght];
                tableauuserid = new String[lenght];
                for(int i =0; i<lenght ; i++) {
                    tableauusername[i] = user.get(i).getFirstandSur();
                    tableauuserid[i] = Integer.toString(user.get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(RechAjouterEleveAClasse.this,
                        android.R.layout.simple_list_item_1, tableauusername);
                listes.setAdapter(adapterliste);
                loupe.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        textrecherche = recherche.getText().toString();
                        longueur =0;
                        for (int y = 0; y < tableauusername.length; y++) {
                            if (tableauusername[y].contains(textrecherche) == true) {
                                longueur = longueur+1;
                            }
                        }
                        tableautrié = new String[longueur];
                        tableautriéid = new String[longueur];
                        int o=0;
                        for (int y = 0; y < tableauusername.length; y++) {
                            if (tableauusername[y].contains(textrecherche) == true) {
                                tableautrié[o]=tableauusername[y];
                                tableautriéid[o]=tableauuserid[y];
                                o++;
                            }
                        }
                        if (tableautrié.length==0) {
                            Toast.makeText(RechAjouterEleveAClasse.this, "pas de classes contenant ce nom", Toast.LENGTH_SHORT).show();
                        } else {
                            final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(RechAjouterEleveAClasse.this,
                                    android.R.layout.simple_list_item_1, tableautrié);
                            listes.setAdapter(adapterliste);
                        }
                        listes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println(tableautriéid[position]);
                                iduser = tableautriéid[position];
                                Intent versmots = new Intent (RechAjouterEleveAClasse.this, Envoyerdemandeprof.class);
                                Bundle b = new Bundle();
                                b.putString("id",idreçu);
                                b.putString("iduser",iduser);
                                b.putString("idclasse",idclasse);
                                versmots.putExtras(b);
                                startActivity(versmots);
                                finish();
                            }
                        });

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
