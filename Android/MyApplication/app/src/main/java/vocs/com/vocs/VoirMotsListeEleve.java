package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;

public class VoirMotsListeEleve extends AppCompatActivity {

    ImageButton parametres, retourarriere;
    ListView listviewmots;
    private String idclasse,idreçu,idliste,tableauword[],tableautrad[],tableau[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_mots_liste_eleve);

        parametres = (ImageButton) findViewById(R.id.parametress);
        retourarriere = (ImageButton) findViewById(R.id.retourarrieredegroupe);
        listviewmots = (ListView) findViewById(R.id.listviewmots);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
            idclasse = y.getString("idclasse");
            idliste = y.getString("idliste");
        }

        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        githubService.accederliste(idliste,new retrofit.Callback<MotsListe>() {
            @Override
            public void success(MotsListe motsliste, Response response) {
                int lenght = motsliste.getWordTrads().size();
                tableauword = new String[lenght];
                tableautrad = new String[lenght];
                tableau = new String[lenght];
                for(int i=0; i<lenght; i++) {
                    tableauword[i]=motsliste.getWordTrads().get(i).getWord().getContent();
                    tableautrad[i]=motsliste.getWordTrads().get(i).getTrad().getContent();
                    tableau[i]=(tableauword[i]+" - "+tableautrad[i]);
                }

                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(VoirMotsListeEleve.this,
                        android.R.layout.simple_list_item_1, tableau);
                listviewmots.setAdapter(adapterliste);
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (VoirMotsListeEleve.this, Parametres.class);
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
                Intent versretour= new Intent (VoirMotsListeEleve.this, VoirSaClasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });
    }
}
