package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.envoiedemande;
import static vocs.com.vocs.R.id.listes;
import static vocs.com.vocs.R.id.loupe;
import static vocs.com.vocs.R.id.nom;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.recherche;
import static vocs.com.vocs.R.id.retour;
import static vocs.com.vocs.R.id.role;

public class AccepterPartage extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,idliste,iduser,nom;
    TextView textaccepter;
    Button oui,non;
    private DemandePartage demands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepter_partage);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        oui=(Button) findViewById(R.id.oui);
        non=(Button) findViewById(R.id.non);
        textaccepter=(TextView) findViewById(R.id.textaccepter);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idliste = b.getString("idliste");
            iduser = b.getString("iduser");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (AccepterPartage.this, Parametres.class);
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
                Intent versparam= new Intent (AccepterPartage.this, MotsProf.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                b.putString("etat","pas de demande");
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
        System.out.println(iduser);
        GitService githubService = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build()
                .create(GitService.class);

        System.out.println(iduser);
        githubService.accederaunuser(iduser,new retrofit.Callback<User>() {
            @Override
            public void success(User user, Response response) {
                nom=user.getFirstandSur();
                System.out.println(nom);
                textaccepter.setText("Voulez vous partager votre liste à "+nom+" ?");
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        oui.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                        GitService githubService = new RestAdapter.Builder()
                                .setEndpoint(ENDPOINT)
                                .build()
                                .create(GitService.class);
                        demands = new DemandePartage();
                        demands.setUserSend(Integer.parseInt(idreçu));
                        demands.setUserReceive(Integer.parseInt(iduser));
                        demands.setListe(Integer.parseInt(idliste));
                        githubService.envoyerdemandepartage(demands,new retrofit.Callback<GetDemands>() {
                            @Override
                            public void success(GetDemands demande, Response response) {
                                Toast.makeText(AccepterPartage.this,"demande envoyée",Toast.LENGTH_SHORT).show();
                                Intent versparam= new Intent (AccepterPartage.this, ResultPartageListeProf.class);
                                Bundle b = new Bundle();
                                b.putString("id",idreçu);
                                b.putString("idliste",idliste);
                                b.putString("etat","envoie");
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
        non.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (AccepterPartage.this, ResultPartageListeProf.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idliste",idliste);
                b.putString("etat","pas envoie");
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });
    }
}
