package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.classe;
import static vocs.com.vocs.R.id.listviewclasses;

public class Envoyerdemandeetudiant extends AppCompatActivity {

    ImageButton parametres,retour;
    ListView listviewmembres;
    TextView nomclasse,prof;
    String idreçu,idclasse,tableauuser[],role[],leprof;
    Button envoiedemande;
    private Demands demands;
    private String idprof;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyerdemandeetudiant);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour = (ImageButton) findViewById(R.id.retourarriere);
        listviewmembres = (ListView) findViewById(R.id.listviewmembres);
        nomclasse = (TextView) findViewById(R.id.nomclasse);
        envoiedemande = (Button) findViewById(R.id.envoiedemande);
        prof = (TextView) findViewById(R.id.prof);

        Bundle b = getIntent().getExtras();

        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Envoyerdemandeetudiant.this, Parametres.class);
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
                Intent versintegr= new Intent (Envoyerdemandeetudiant.this, Integrerclasse.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versintegr.putExtras(b);
                startActivity(versintegr);
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
                nomclasse.setText(classes.getName().concat(" :"));
                int lenght = classes.getUsers().size();
                System.out.println(lenght);
                role = new String[lenght];
                tableauuser = new String[lenght];
                for(int y=0;y<lenght;y++){
                    role=classes.getUsers().get(y).getRoles();
                    if(role[0].contentEquals("ROLE_PROFESSOR")){
                        leprof = classes.getUsers().get(y).getSurname().concat(" ").concat(classes.getUsers().get(y).getFirstname());
                        idprof = Integer.toString(classes.getUsers().get(y).getId());
                    }
                    System.out.println(leprof);
                    System.out.println(idprof);
                    tableauuser[y]=classes.getUsers().get(y).getSurname().concat(" ").concat(classes.getUsers().get(y).getFirstname());
                }
                prof.setText("prof : ".concat(leprof));
                final ArrayAdapter<String> adapterliste = new ArrayAdapter<String>(Envoyerdemandeetudiant.this,
                        android.R.layout.simple_list_item_1, tableauuser);
                listviewmembres.setAdapter(adapterliste);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        envoiedemande.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                demands = new Demands();
                demands.setUserSend(Integer.parseInt(idreçu));
                demands.setUserReceive(Integer.parseInt(idprof));
                demands.setClasse(Integer.parseInt(idclasse));
                githubService.envoyerdemande(demands,new retrofit.Callback<GetDemands>() {
                    @Override
                    public void success(GetDemands demande, Response response) {
                        Toast.makeText(Envoyerdemandeetudiant.this,"demande envoyée",Toast.LENGTH_SHORT).show();
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
