package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.id.list;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.listView;
import static vocs.com.vocs.R.id.listviewajoutliste;
import static vocs.com.vocs.R.id.listviewclasseduprof;
import static vocs.com.vocs.R.id.nomclasse;

public class ListesDuneClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    ListView liste;
    private String idreçu,idclasse,idliste,tableauidliste[],etat;
    String tableauliste[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes_dune_classe);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        liste=(ListView) findViewById(R.id.liste);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            etat = b.getString("etat");
        }

        if(etat!=null){
            if(etat.contentEquals("non")){
                Toast.makeText(ListesDuneClasse.this,"La liste n'a pas été supprimée de la classe",Toast.LENGTH_LONG).show();
            }
            if(etat.contentEquals("oui")){
                Toast.makeText(ListesDuneClasse.this,"La liste a été supprimée de la classe",Toast.LENGTH_SHORT).show();
            }
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (ListesDuneClasse.this, Parametres.class);
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
                Intent versparam= new Intent (ListesDuneClasse.this, Classeduprof.class);
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
                tableauliste = new String[lenght];
                tableauidliste = new String[lenght];
                for(int i=0; i<lenght; i++){
                    tableauliste[i]=classes.getLists().get(i).getName();
                    tableauidliste[i]=Integer.toString(classes.getLists().get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(ListesDuneClasse.this,
                        android.R.layout.simple_list_item_1, tableauliste);
                liste.setAdapter(adapterliste);
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });
        githubService.obtenirlistsclasses(idclasse,new retrofit.Callback<List<Liste>>() {
            @Override
            public void success(List<Liste> liste, Response response) {

            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idliste = tableauidliste[position];
                Intent versmots = new Intent (ListesDuneClasse.this, DeleteListeClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                b.putString("idliste",idliste);
                versmots.putExtras(b);
                startActivity(versmots);
                finish();
            }
        });
    }
}
