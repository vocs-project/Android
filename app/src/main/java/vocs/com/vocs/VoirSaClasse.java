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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.BottomBar;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.email;
import static vocs.com.vocs.R.id.nom;
import static vocs.com.vocs.R.id.role;

public class VoirSaClasse extends AppCompatActivity {

    ImageButton parametres, retourarriere;
    private String idreçu,idclasse,tableauliste[],tableauidliste[],idliste;
    TextView classename;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_sa_classe);

        parametres = (ImageButton) findViewById(R.id.parametress);
        retourarriere = (ImageButton) findViewById(R.id.retourarrieredegroupe);
        classename = (TextView) findViewById(R.id.classe);
        listview = (ListView) findViewById(R.id.listview);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
            idclasse = y.getString("idclasse");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (VoirSaClasse.this, Parametres.class);
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
                Intent versretour= new Intent (VoirSaClasse.this, Groupe.class);
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

        githubService.obtenirclasses(idclasse,new retrofit.Callback<Classes>() {
            @Override
            public void success(Classes classe, Response response) {
                System.out.println(classe.getLists().size());
                classename.setText(classe.getName()+" :");
                int lenght = classe.getLists().size();
                if(lenght == 0 ) {
                    Toast.makeText(VoirSaClasse.this, "Cette classe ne possède pas de liste", Toast.LENGTH_SHORT).show();
                }
                tableauliste = new String[lenght];
                tableauidliste = new String[lenght];
                for(int i=0; i<lenght; i++){
                    tableauliste[i]=classe.getLists().get(i).getName();
                    tableauidliste[i]=Integer.toString(classe.getLists().get(i).getId());
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(VoirSaClasse.this,
                        android.R.layout.simple_list_item_1, tableauliste);
                listview.setAdapter(adapterliste);
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idliste = tableauidliste[position];
                Intent versmots = new Intent (VoirSaClasse.this, VoirMotsListeEleve.class);
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
