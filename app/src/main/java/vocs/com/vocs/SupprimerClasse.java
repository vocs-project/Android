package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.listviewclasseduprof;
import static vocs.com.vocs.R.id.nomclasse;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retour;

public class SupprimerClasse extends AppCompatActivity {

    ImageButton parametres,retour;
    private String idreçu,idclasse;
    Button oui,non;
    TextView nomclasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supprimer_classe);

        parametres=(ImageButton) findViewById(R.id.parametres);
        retour=(ImageButton) findViewById(R.id.retourarriere);
        nomclasse = (TextView) findViewById(R.id.classe);
        oui = (Button) findViewById(R.id.oui);
        non = (Button) findViewById(R.id.non);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            idreçu = b.getString("id");
            idclasse = b.getString("idclasse");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (SupprimerClasse.this, Parametres.class);
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
                Intent versparam= new Intent (SupprimerClasse.this, Classeduprof.class);
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
               nomclasse.setText(classes.getName());
            }
            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
            }
        });

        non.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (SupprimerClasse.this, Classeduprof.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                b.putString("idclasse",idclasse);
                versparam.putExtras(b);
                startActivity(versparam);
                finish();
            }
        });

        oui.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);

                githubService.deleteclasse(idclasse,new retrofit.Callback<Classe>() {
                    @Override
                    public void success(Classe classe, Response response) {
                        Intent versparam= new Intent (SupprimerClasse.this, GererClasses.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        b.putString("idclasse",idclasse);
                        b.putString("etat","suppr");
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
