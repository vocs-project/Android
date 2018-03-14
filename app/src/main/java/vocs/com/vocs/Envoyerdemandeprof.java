package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewmembres;
import static vocs.com.vocs.R.id.nomclasse;

import static vocs.com.vocs.R.id.role;

public class Envoyerdemandeprof extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,idclasse,iduser;
    TextView nomeleve;
    Button adddemand;
    Demands demands;
    private String role[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyerdemandeprof);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        adddemand = (Button) findViewById(R.id.adddemand);
        nomeleve = (TextView) findViewById(R.id.nomeleve);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
            iduser=b.getString("iduser");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (Envoyerdemandeprof.this, Parametres.class);
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
                Intent versparam= new Intent (Envoyerdemandeprof.this, RechAjouterEleveAClasse.class);
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

        githubService.accederaunuser(iduser,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                nomeleve.setText(user.getFirstandSur());
                if(user.getRoles().length>0) {
                    role = new String[user.getRoles().length];
                    role = user.getRoles();
                }
                else{
                    role = new String[1];
                    role[0]=" ";
                }
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        adddemand.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                demands = new Demands();
                demands.setUserSend(Integer.parseInt(idreçu));
                demands.setUserReceive(Integer.parseInt(iduser));
                demands.setClasse(Integer.parseInt(idclasse));
                if(role[0].contentEquals("ROLE_PROFESSOR")){
                    Toast.makeText(Envoyerdemandeprof.this,"Vous ne pouvez pas ajouter de professeur à vos classes",Toast.LENGTH_SHORT).show();
                }
                else {
                    githubService.envoyerdemande(demands, new retrofit.Callback<GetDemands>() {
                        @Override
                        public void success(GetDemands demande, Response response) {
                            Intent versparam = new Intent(Envoyerdemandeprof.this, RechAjouterEleveAClasse.class);
                            Bundle b = new Bundle();
                            b.putString("id", idreçu);
                            b.putString("idclasse", idclasse);
                            b.putString("etat", "envoie");
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
            }
        });
    }
}
