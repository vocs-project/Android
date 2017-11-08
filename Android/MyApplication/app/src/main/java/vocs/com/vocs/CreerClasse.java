package vocs.com.vocs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.R.attr.y;
import static vocs.com.vocs.GitService.ENDPOINT;
import static vocs.com.vocs.R.id.editemail;
import static vocs.com.vocs.R.id.editnom;
import static vocs.com.vocs.R.id.editpassword;
import static vocs.com.vocs.R.id.editprenom;
import static vocs.com.vocs.R.id.parametres;
import static vocs.com.vocs.R.id.retourarriere;
import static vocs.com.vocs.R.id.role;

public class CreerClasse extends AppCompatActivity {

    ImageButton parametres,retourarriere;
    String idreçu,motrentre;
    EditText nomclasse;
    Button valider;
    Postclasse nvclasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_classe);

        parametres = (ImageButton) findViewById(R.id.parametres);
        retourarriere = (ImageButton) findViewById(R.id.retourarriere);
        nomclasse = (EditText) findViewById(R.id.nomclasse);
        valider = (Button) findViewById(R.id.valider);

        Bundle y = getIntent().getExtras();
        if(y != null) {
            idreçu = y.getString("id");
        }

        parametres.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent versparam= new Intent (CreerClasse.this, Parametres.class);
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
                Intent versretour= new Intent (CreerClasse.this, GererClasses.class);
                Bundle b = new Bundle();
                b.putString("id",idreçu);
                versretour.putExtras(b);
                startActivity(versretour);
                finish();
            }
        });

        valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                motrentre = nomclasse.getText().toString();
                GitService githubService = new RestAdapter.Builder()
                        .setEndpoint(ENDPOINT)
                        .build()
                        .create(GitService.class);
                nvclasse = new Postclasse();
                nvclasse.setName(motrentre);
                githubService.creerclasse(idreçu,nvclasse,new retrofit.Callback<Postclasse>() {
                    @Override
                    public void success(Postclasse classe, Response response) {
                        Intent versretour= new Intent (CreerClasse.this, GererClasses.class);
                        Bundle b = new Bundle();
                        b.putString("id",idreçu);
                        versretour.putExtras(b);
                        startActivity(versretour);
                        finish();
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(CreerClasse.this, "Une classe avec ce nom existe déjà", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
