package vocs.com.vocs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.BottomBar;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.email;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retourarriere;
import static vocs.com.vocs.R.id.role;

public class Integrerclasse extends AppCompatActivity {

    ImageButton parametres,retourarriere,loupe;
    EditText recherche;
    ListView listviewclasses;
    String idreçu,tableauclassename[],tableauclasseid[],idclasse,textrecherche,tableautrié[],tableautriéid[];
    int longueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integrerclasse);

        parametres = (ImageButton) findViewById(R.id.parametress);
        retourarriere = (ImageButton) findViewById(R.id.retourarrieredegroupe);
        listviewclasses = (ListView) findViewById(R.id.listviewclasses);
        recherche = (EditText) findViewById(R.id.recherche);
        loupe = (ImageButton) findViewById(R.id.loupe);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
        }


        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Integrerclasse.this, Parametres.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        retourarriere.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versretour= new Intent (Integrerclasse.this, Groupe.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.getclasses(new retrofit.Callback<List<Classes>>() {
            @Override
            public void success(List<Classes> classes, Response response) {
                final int lenght = classes.size();
                tableauclassename = new String[lenght];
                tableauclasseid = new String[lenght];
                for(int i =0; i<lenght ; i++) {
                    tableauclassename[i] = classes.get(i).getName();
                    tableauclasseid[i] = Integer.toString(classes.get(i).getId());
                }
                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(Integrerclasse.this,
                        android.R.layout.simple_list_item_1, tableauclassename);
                listviewclasses.setAdapter(adapterliste);
                loupe.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                            textrecherche = recherche.getText().toString();
                            longueur =0;
                            for (int y = 0; y < tableauclassename.length; y++) {
                                if (tableauclassename[y].contains(textrecherche) == true) {
                                     longueur = longueur+1;
                                }
                            }
                            tableautrié = new String[longueur];
                        tableautriéid = new String[longueur];
                            int o=0;
                             for (int y = 0; y < tableauclassename.length; y++) {
                                if (tableauclassename[y].contains(textrecherche) == true) {
                                    tableautrié[o]=tableauclassename[y];
                                    tableautriéid[o]=tableauclasseid[y];
                                    o++;
                                }
                             }
                            if (tableautrié.length==0) {
                                Toast.makeText(Integrerclasse.this, "pas de classes contenant ce nom", Toast.LENGTH_SHORT).show();
                            } else {
                                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(Integrerclasse.this,
                                        android.R.layout.simple_list_item_1, tableautrié);
                                listviewclasses.setAdapter(adapterliste);
                            }
                        listviewclasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                System.out.println(tableautriéid[position]);
                                idclasse = tableautriéid[position];
                                Intent versmots = new Intent (Integrerclasse.this, Envoyerdemandeetudiant.class);
                                Bundle b = new Bundle();
                                b.putString("id",idreçu);
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

        listviewclasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idclasse = tableauclasseid[position];
                Intent versmots = new Intent (Integrerclasse.this,Envoyerdemandeetudiant.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versmots.putExtras(b);
                startActivity(versmots);
                finish();
            }
        });
    }
}
